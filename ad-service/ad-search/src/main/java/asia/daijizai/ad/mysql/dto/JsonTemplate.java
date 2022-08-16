package asia.daijizai.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/5 21:20
 * @description 表示整个模板文件（template.json）
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTemplate {
    private String DBName;
    private List<JsonTable> tableList;
}
