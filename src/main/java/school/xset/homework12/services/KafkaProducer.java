package school.xset.homework12.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    LogServiceProducer logService = new LogServiceProducer();
    @Value("${spring.kafka.producer.topic.out}")
    private String topic;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(topic, message);
        logService.writeLog(topic, message);
    }

}
