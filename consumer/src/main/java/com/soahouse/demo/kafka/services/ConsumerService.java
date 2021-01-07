package com.soahouse.demo.kafka.services;

import gov.dwp.citizen.address.Address;
import gov.dwp.citizen.address.AddressKey;
import gov.dwp.citizen.death.DeathDateUpdated;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j

@KafkaListener(topics = "${demo.topic-name:topicName}",
        containerFactory = "kafkaListenerContainerFactory",
        groupId = "test-group")
public class ConsumerService {

        @KafkaHandler
        public void listen(@Payload Address record) {

                log.info(record.toString());
        }

        @KafkaHandler
        public void listen(@Payload DeathDateUpdated record) {

                log.info(record.toString());
        }
}
