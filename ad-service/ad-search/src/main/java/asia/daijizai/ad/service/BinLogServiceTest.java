package asia.daijizai.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/5 20:38
 * @description 我们之所以监听BinLog，构造增量数据，是因为我们希望检索系统与投放系统解耦。
 * 投放系统在不停工作中，广告主就有可能对广告进行增删改，投放系统又不想与检索系统产生关联，所以我们去主动监听所写入的MySQL的BinLog，实现更新
 */
public class BinLogServiceTest {

/*

    >>>WRITE<<<
    WriteRowsEventData{
        tableId=85,
        includedColumns={0, 1, 2},
        rows=[
            [10, 10, 宝马]
        ]
    }

    >>>UPDATE<<<
    UpdateRowsEventData{
        tableId=85,
        includedColumnsBeforeUpdate={0, 1, 2},
        includedColumns={0, 1, 2},
        rows=[
            {before=[10, 10, 宝马], after=[10, 11, 宝马]}
        ]
    }

    >>>DELETE<<<
    DeleteRowsEventData{
        tableId=85,
        includedColumns={0, 1, 2},
        rows=[
            [11, 10, 奔驰]
        ]
    }


    >>>WRITE<<<
    WriteRowsEventData{
        tableId=70,
        includedColumns={0, 1, 2, 3, 4, 5, 6, 7},
        rows=[
            [12, 10, plan, 1, Tue Jan 01 08:00:00 CST 2019, Tue Jan 01 08:00:00 CST 2019, Tue Jan 01 08:00:00 CST 2019, Tue Jan 01 08:00:00 CST 2019]
        ]
    }

    >>>WRITE<<<
    WriteRowsEventData{tableId=104, includedColumns={0, 1, 2}, rows=[
        [13, 11, 老龙凤]
    ]}


    UPDATE ad_unit_keyword SET keyword='谎言' WHERE unit_id=10;
    >>>UPDATE<<<
    UpdateRowsEventData{tableId=104, includedColumnsBeforeUpdate={0, 1, 2}, includedColumns={0, 1, 2}, rows=[
        {before=[10, 10, 奔驰], after=[10, 10, 谎言]},
        {before=[11, 10, 牛], after=[11, 10, 谎言]},
        {before=[12, 10, 哈哈], after=[12, 10, 谎言]}
    ]}


 */

    public static void main(String[] args) throws Exception{
        BinaryLogClient client=new BinaryLogClient(
                "127.0.0.1",
                3306,
                "root",
                "123456"
        );
        client.registerEventListener(event -> {
            EventData data = event.getData();

            if(data instanceof UpdateRowsEventData){
                System.out.println(">>>UPDATE<<<");
                System.out.println(data);
            } else if(data instanceof WriteRowsEventData){
                System.out.println(">>>WRITE<<<");
                System.out.println(data);
            }else if(data instanceof DeleteRowsEventData){
                System.out.println(">>>DELETE<<<");
                System.out.println(data);
            }
        });

        client.connect();
    }
}
