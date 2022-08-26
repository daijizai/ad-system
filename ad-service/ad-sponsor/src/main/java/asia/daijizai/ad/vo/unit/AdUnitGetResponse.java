package asia.daijizai.ad.vo.unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/24 11:46
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AdUnitGetResponse {
    private String id;
    private String unitName;
    private String positionType;
    private String budget;
}
