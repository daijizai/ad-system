package asia.daijizai.ad.mysql.listener;

import asia.daijizai.ad.mysql.constant.Constant;
import asia.daijizai.ad.mysql.constant.OpType;
import asia.daijizai.ad.mysql.dto.BinlogRowData;
import asia.daijizai.ad.mysql.dto.MySqlRowData;
import asia.daijizai.ad.mysql.dto.JsonTableInfo;
import asia.daijizai.ad.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/7 12:08
 * @description
 */

@Slf4j
@Component
public class IncrementListener implements IListener{

    @Resource(name = "indexSender")
    private ISender sender;

    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    //@PostConstruct是Java自带的注解，在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register db and table info");
        //k是tableName，v是DBName
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    //将BinlogRowData转换为MySqlRowData，将MySqlRowData投递
    @Override
    public void onEvent(BinlogRowData binlogRowData) {
        JsonTableInfo jsonTableInfo = binlogRowData.getTable();
        OpType opType = OpType.to(binlogRowData.getEventType());

        //包装成最后需要投递的数据
        MySqlRowData mySqlRowData=new MySqlRowData()
                .setTableName(jsonTableInfo.getTableName())
                .setLevel(binlogRowData.getTable().getLevel())
                .setOpType(opType);

        //取出模板中该操作对应的字段列表
        List<String> fieldList = jsonTableInfo.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, jsonTableInfo.getTableName());
            return;
        }

        for (Map<String, String> afterMap : binlogRowData.getAfter()) {

            Map<String, String> fieldValueMap = new HashMap<>();

            for (Map.Entry<String, String> entry : afterMap.entrySet()) {

                String colName = entry.getKey();
                String colValue = entry.getValue();

                fieldValueMap.put(colName, colValue);
            }

            mySqlRowData.getFieldValueMaps().add(fieldValueMap);
        }

        sender.sender(mySqlRowData);//实现增量数据的投递
    }
}
