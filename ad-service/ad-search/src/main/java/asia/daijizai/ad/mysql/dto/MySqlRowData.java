package asia.daijizai.ad.mysql.dto;

import asia.daijizai.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/7 11:13
 * @description BinlogRowData -> MySqlRowData。
 * 通过BinlogRowData创建MySqlRowData用来构造增量数据。
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MySqlRowData {

    private String tableName;
    private String level;
    private OpType opType;
    private List<Map<String, String>> fieldValueMaps = new ArrayList<>();
}
