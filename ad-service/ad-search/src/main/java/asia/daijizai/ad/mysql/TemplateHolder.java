package asia.daijizai.ad.mysql;

import asia.daijizai.ad.mysql.constant.OpType;
import asia.daijizai.ad.mysql.dto.JsonTemplateInfo;
import asia.daijizai.ad.mysql.dto.JsonTableInfo;
import asia.daijizai.ad.mysql.dto.JsonTemplate;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/6 10:54
 * @description
 */

@Slf4j
@Component
public class TemplateHolder {

    private final String SQL_SCHEMA = "select table_schema, table_name, column_name, ordinal_position " +
            "from information_schema.columns " +
            "where table_schema = ? and table_name = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JsonTemplateInfo jsonTemplateInfo;

    @PostConstruct
    private void init() {
        log.info("TemplateHolder Init");
        loadJson("template.json");
    }

    /**
     * 通过表的名字，拿到表的所有信息
     *
     * @param tableName
     * @return
     */
    public JsonTableInfo getTable(String tableName) {
        return jsonTemplateInfo.getTableTemplateMap().get(tableName);
    }

    //加载配置文件
    private void loadJson(String path) {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inStream = cl.getResourceAsStream(path);

        try {
            JsonTemplate jsonTemplate = JSON.parseObject(inStream, Charset.defaultCharset(), JsonTemplate.class);//反序列化
            /*
            序列化应该注意对应关系
            template.json文件中表示数据库名字字段为 database
            类JsonTemplate中表示数据库名字字段为 DBName
             */
            log.info(">>>>>{}",jsonTemplate);

            this.jsonTemplateInfo = JsonTemplateInfo.parse(jsonTemplate);// JsonTemplate -> JsonTemplateInfo
            log.info(">>>>>{}",this.jsonTemplateInfo);

            loadMeta();//填充posMap字段。JsonTemplateInfo -> JsonTableInfo -> posMap

            log.info(">>>>>{}",this.jsonTemplateInfo);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("fail to parse json file");
        }
    }

    //查询每张表的schema信息，实现字段索引到字段名的映射
    private void loadMeta() {
        for (Map.Entry<String, JsonTableInfo> entry : this.jsonTemplateInfo.getTableTemplateMap().entrySet()) {
            JsonTableInfo jsonTableInfo = entry.getValue();

            List<String> updateFields = jsonTableInfo.getOpTypeFieldSetMap().get(OpType.UPDATE);
            List<String> insertFields = jsonTableInfo.getOpTypeFieldSetMap().get(OpType.ADD);
            List<String> deleteFields = jsonTableInfo.getOpTypeFieldSetMap().get(OpType.DELETE);

            Object[] paramValues = new Object[]{this.jsonTemplateInfo.getDBName(), jsonTableInfo.getTableName()};
            jdbcTemplate.query(
                    SQL_SCHEMA,
                    paramValues,
                    (rs, i) -> {

                        int pos = rs.getInt("ORDINAL_POSITION");
                        String colName = rs.getString("COLUMN_NAME");

                        if ((null != updateFields && updateFields.contains(colName))//包含这个字段才需要记录
                                || (null != insertFields && insertFields.contains(colName))
                                || (null != deleteFields && deleteFields.contains(colName))) {
                            jsonTableInfo.getPosMap().put(pos - 1, colName);
                        }

                        return null;
                    }
            );
        }
    }


}
