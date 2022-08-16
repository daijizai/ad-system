package asia.daijizai.ad.mysql.dto;

import asia.daijizai.ad.mysql.constant.OpType;
import asia.daijizai.ad.util.CommonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/5 21:38
 * @description 对模板文件（template.json）的java对象（JsonTemplate）进行解析，构造一份模板文件的对象
 */

@Data
@NoArgsConstructor
public class JsonTemplateInfo {

    private String DBName;
    private Map<String, JsonTableInfo> tableTemplateMap = new HashMap<>();//key是表的名称，value是表的属性

    private JsonTemplateInfo(String DBName) {
        this.DBName = DBName;
    }

    public static JsonTemplateInfo parse(JsonTemplate jsonTemplate) {
        JsonTemplateInfo jsonTemplateInfo = new JsonTemplateInfo(jsonTemplate.getDBName());

        for (JsonTable jsonTable : jsonTemplate.getTableList()) {

            JsonTableInfo tableTemplate = new JsonTableInfo(jsonTable.getTableName(), jsonTable.getLevel().toString());

            jsonTemplateInfo.tableTemplateMap.put(tableTemplate.getTableName(), tableTemplate);

            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();

            //遍历操作类型对应的列
            for (JsonTable.Column column : jsonTable.getInsert()) {
                List<String> addColumns = CommonUtil.getOrCreate(OpType.ADD, opTypeFieldSetMap, ArrayList::new);
                addColumns.add(column.getColumn());
            }

            for (JsonTable.Column column : jsonTable.getUpdate()) {
                CommonUtil.getOrCreate(OpType.UPDATE, opTypeFieldSetMap, ArrayList::new)
                        .add(column.getColumn());
            }

            for (JsonTable.Column column : jsonTable.getDelete()) {
                CommonUtil.getOrCreate(OpType.DELETE, opTypeFieldSetMap, ArrayList::new)
                        .add(column.getColumn());
            }
        }

        return jsonTemplateInfo;
    }

}
