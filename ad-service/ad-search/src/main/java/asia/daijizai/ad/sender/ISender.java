package asia.daijizai.ad.sender;

import asia.daijizai.ad.mysql.dto.MySqlRowData;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/7 12:02
 * @description Sender n.发送人；邮寄人
 * 增量数据投放给索引服务，实现增量数据到增量索引的构建工作
 * 实现ISender接口实现增量数据的投递
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
