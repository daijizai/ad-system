package asia.daijizai.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/6 17:19
 * @description 解析binlog成为我们系统中的java对象，实现Event->BinlogRowData的映射
 *     public interface EventListener {
 *
 *         void onEvent(Event event);
 *     }
 */

@Data
public class BinlogRowData {

    private JsonTableInfo table;

    private EventType eventType;

    private List<Map<String, String>> after;

    //更改之前的数据对我们不重要，我们只关心更改后的数据
    private List<Map<String, String>> before;
}
