package asia.daijizai.ad.vo.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/24 11:22
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AdPlanGetResponse {
    private String id;
    private String planName;
    private String startDate;
    private String endDate;
}
