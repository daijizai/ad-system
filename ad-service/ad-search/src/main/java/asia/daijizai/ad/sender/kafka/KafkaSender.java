package asia.daijizai.ad.sender.kafka;

import asia.daijizai.ad.mysql.dto.MySqlRowData;
import asia.daijizai.ad.sender.ISender;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/13 10:38
 * @description
 */
@Component("kafkaSender")
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void sender(MySqlRowData rowData) {

        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(message.toString(), MySqlRowData.class);

            System.out.println("kafka processMysqlRowData: " + JSON.toJSONString(rowData));
        }
    }



}
