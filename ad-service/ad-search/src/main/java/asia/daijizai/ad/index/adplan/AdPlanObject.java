package asia.daijizai.ad.index.adplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/2 21:36
 * @description 推广计划索引对象类，我们会把数据表的实体类翻译成索引对象类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanObject {

    private Long planId;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;

    public void update(AdPlanObject newObject) {

        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }
        if (null != newObject.getUserId()) {
            this.userId = newObject.getUserId();
        }
        if (null != newObject.getPlanStatus()) {
            this.planStatus = newObject.getPlanStatus();
        }
        if (null != newObject.getStartDate()) {
            this.startDate = newObject.getStartDate();
        }
        if (null != newObject.getEndDate()) {
            this.endDate = newObject.getEndDate();
        }
    }
}
