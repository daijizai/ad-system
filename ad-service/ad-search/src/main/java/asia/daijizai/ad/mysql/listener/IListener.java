package asia.daijizai.ad.mysql.listener;

import asia.daijizai.ad.mysql.dto.BinlogRowData;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/6 17:28
 * @description binlog日志数据，增量索引更新
 */
public interface IListener {

    //我们可以对不同的表，定义不同的更新方法，所以我们需要注册不同的监听器
    void register();

    void onEvent(BinlogRowData eventData);
}
