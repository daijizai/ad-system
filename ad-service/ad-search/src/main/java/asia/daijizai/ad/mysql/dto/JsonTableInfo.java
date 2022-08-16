package asia.daijizai.ad.mysql.dto;

import asia.daijizai.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/5 21:27
 * @description 为了操作时方便读取表的信息，方便解析binlog（JsonTable -> JsonTableInfo）
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTableInfo {

    private String tableName;
    private String level;

    public JsonTableInfo(String tableName, String level){
        this.tableName=tableName;
        this.level=level;
    }

    //操作类型 -> 字段列表
    private Map<OpType, List<String>> opTypeFieldSetMap=new HashMap<>();

    /**
     * 字段索引 -> 字段名
     */
    private Map<Integer,String> posMap=new HashMap<>();
}
