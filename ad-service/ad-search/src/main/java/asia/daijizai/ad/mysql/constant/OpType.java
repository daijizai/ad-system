package asia.daijizai.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/4 13:56
 * @description
 */
public enum OpType {

    ADD,
    UPDATE,
    DELETE,
    OTHER;

    public static OpType to(EventType eventType){
        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
