package asia.daijizai.ad.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/7/31 21:33
 * @description
 */

@Getter
public enum CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
