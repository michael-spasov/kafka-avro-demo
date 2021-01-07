package com.soahouse.demo.kafka.services;

import gov.dwp.citizen.address.Address;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j

@KafkaListener(topics = "${demo.topic-name:topicName}",
        containerFactory = "kafkaListenerContainerFactory",
        groupId = "test-group", errorHandler = "kafkaListenerErrorHandler")
public class ConsumerService {

        @KafkaHandler
        public void listen(@Payload Address record, ConsumerRecordMetadata meta) {

                log.info(record.toString());
        }

        @KafkaHandler(isDefault = true)
        public void listenDefault(@Payload SpecificRecord object, ConsumerRecordMetadata meta) {
                log.info(object.toString());
        }
}
