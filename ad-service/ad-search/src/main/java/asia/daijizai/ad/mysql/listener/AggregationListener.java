package asia.daijizai.ad.mysql.listener;

import asia.daijizai.ad.mysql.TemplateHolder;
import asia.daijizai.ad.mysql.dto.BinlogRowData;
import asia.daijizai.ad.mysql.dto.JsonTableInfo;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/6 17:37
 * @description aggregation n.聚集，集合，聚集体；集合体；聚合作用；凝聚；聚集作用；总量
 */

@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private final TemplateHolder templateHolder;
    private String DBName;
    private String tableName;
    private Map<String, IListener> listenerMap = new HashMap<>();//key: DBName+tableName；value: 处理方法

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genKey(String DBName, String tableName) {
        return DBName + ":" + tableName;
    }

    public void register(String dbName, String tableName, IListener ilistener) {
        log.info("register : {}-{}", dbName, tableName);
        this.listenerMap.put(genKey(dbName, tableName), ilistener);
    }

    //对event进行解析，目标是将event变成BinlogRowData，然后将BinlogRowData传递给listener，实现增量数据的更新
    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type: {}", type);

        //row格式的binlog文件中，每个ROW_EVENT之前都有一个TABLE_MAP_EVENT，用于描述表的内部ID和结构定义。
        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.DBName = data.getDatabase();
            this.tableName = data.getTable();
            return;
        }

        //除了TABLE_MAP和这三条EXT_~_ROWS其他的都不需要关心
        if (type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        //表名和库名是否已经完成填充
        if (StringUtils.isEmpty(this.DBName) || StringUtils.isEmpty(this.tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找出对应表有兴趣的监听器
        String key = genKey(this.DBName, this.tableName);
        IListener listener = this.listenerMap.get(key);
        if (null == listener) {
            log.debug("skip {}", key);
            return;
        }
        log.info("trigger event: {}", type.name());

        //尝试处理binlog
        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(type);

            //将rowData给感兴趣的处理器处理，即实现增量数据的更新
            listener.onEvent(rowData);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        } finally {
            //处理完一个事件之后，将这两个变量清空
            this.DBName = "";
            this.tableName = "";
        }
    }

    //EventData -> BinlogRowData
    private BinlogRowData buildRowData(EventData eventData) {

        JsonTableInfo jsonTableInfo = templateHolder.getTable(tableName);
        if (null == jsonTableInfo) {
            log.warn("table {} not found", tableName);
            return null;
        }

        List<Map<String, String>> afterMapList = new ArrayList<>();

        for (Serializable[] after : getAfterValues(eventData)) {

            Map<String, String> afterMap = new HashMap<>();

            int colLen = after.length;

            for (int i = 0; i < colLen; i++) {
                // 取出当前位置对应的列名
                String colName = jsonTableInfo.getPosMap().get(i);
                // 如果没有则说明不关心这个列
                if (null == colName) {
                    log.debug("ignore position: {}", i);
                    continue;
                }
                String colValue = after[i].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }

        BinlogRowData rowData = new BinlogRowData();
        rowData.setTable(jsonTableInfo);
        rowData.setAfter(afterMapList);

        return rowData;
    }

    //根据不同的类型（RowsEventData），获取统一的更新后的数据。将三种类型统一（插入和删除看做特殊的更新）
    private List<Serializable[]> getAfterValues(EventData eventData) {

        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        /*
        看UpdateRowsEventData源码。

        private List<Map.Entry<Serializable[], Serializable[]>> rows;
        append("{before=").append(Arrays.toString(row.getKey())).
        append(", after=").append(Arrays.toString(row.getValue())).

        before是key，after是value，我们只需要知道after，所以只要value
         */
        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }
}
