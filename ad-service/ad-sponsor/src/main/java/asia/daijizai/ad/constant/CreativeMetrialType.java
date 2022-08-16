package asia.daijizai.ad.constant;

import lombok.Getter;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 8:38
 * @description
 */

@Getter
public enum CreativeMetrialType {

    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "mp4"),
    AVI(4, "avi"),

    TXT(5, "txt");

    private int type;
    private String desc;

    CreativeMetrialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}