package asia.daijizai.ad.constant;

import lombok.Getter;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 8:36
 * @description
 */

@Getter
public enum CreativeType {

    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TEXT(3, "文本");

    private int type;
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
