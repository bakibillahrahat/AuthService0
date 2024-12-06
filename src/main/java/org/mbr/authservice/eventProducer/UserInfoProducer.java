package org.mbr.authservice.eventProducer;

import org.mbr.authservice.model.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer {
    private final KafkaTemplate<String, UserInfoDto> kafkaTemplete;

    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;

    @Autowired
    UserInfoProducer(KafkaTemplate<String, UserInfoDto> kafkaTemplete){
        this.kafkaTemplete = kafkaTemplete;
    }

    public void sendEventToKafka(UserInfoEvent eventData){
        Message<UserInfoEvent> message = MessageBuilder.withPayload(eventData).setHeader(KafkaHeaders.TOPIC, TOPIC_NAME).build();
        kafkaTemplete.send(message);
    }

}
