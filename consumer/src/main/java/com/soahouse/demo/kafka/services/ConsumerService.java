package com.soahouse.demo.kafka.services;

import gov.dwp.citizen.address.Address;
import gov.dwp.citizen.address.AddressKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

        @KafkaListener(topics = "${demo.topic-name:topicName}",
                containerFactory = "kafkaListenerContainerFactory",
                groupId = "test-group")
        public void listen(ConsumerRecord<AddressKey, Address> record) {
                gov.dwp.citizen.address.Address a = record.value();

                log.info(a.toString());
        }
}
