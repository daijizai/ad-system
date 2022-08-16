package asia.daijizai.ad.index;

import lombok.Getter;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/8 11:49
 * @description 索引里的数据层级
 */

@Getter
public enum DataLevel {

    LEVEL2("2","level 2"),
    LEVEL3("3","level 3"),
    LEVEL4("4","level 4");

    private String level;
    private String desc;

    DataLevel(String level,String desc){
        this.level=level;
        this.desc=desc;
    }
}
