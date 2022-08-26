package asia.daijizai.ad.vo.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/24 13:41
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CreativeGetResponse {
    private String id;
    private String name;
    private String type;
    private String materialType;
    private String height;
    private String width;
    private String size;
    private String duration;
    private String url;
}
