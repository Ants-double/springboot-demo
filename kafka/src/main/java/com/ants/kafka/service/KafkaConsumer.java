package com.ants.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author ants_
 */
@Component
@Slf4j
public class KafkaConsumer {

//    @Autowired
//    private ConsumerFactory consumerFactory;
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaManualAckListenerContainerFactory() {
//            ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
//            new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        //设置提交偏移量的方式 当Acknowledgment.acknowledge()侦听器调用该方法时，立即提交偏移量
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
//        return factory;
//    }

    @KafkaListener(topics = KafkaProducer.TOPIC_TEST1,groupId = KafkaProducer.TOPIC_GROUP1,containerFactory = "kafkaManualAckListenerContainerFactory")
    public void topic_test1(ConsumerRecord<?,?> record, Acknowledgment ack,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test1 消费了：Topic：" + topic + ",Message" + msg);
            ack.acknowledge();
        }
    }

    @KafkaListener(topics = KafkaProducer.TOPIC_TEST2,groupId = KafkaProducer.TOPIC_GROUP2,containerFactory = "kafkaManualAckListenerContainerFactory")
    public void topic_test2(ConsumerRecord<?,?> record, Acknowledgment ack,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test2 消费了：Topic：" + topic + ",Message" + msg);
            ack.acknowledge();
        }
    }
}
