package com.soahouse.demo.kafka.services;

import gov.dwp.citizen.address.Address;
import gov.dwp.citizen.address.AddressKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ProducerService {

        final KafkaTemplate<AddressKey, Address> template;

        @Value("${demo.topic-name:topicName}")
        private String topicName;

        public ProducerService(
                KafkaTemplate<AddressKey, Address> template) {
                this.template = template;
        }

        public void sendMessage() {

                AddressKey key = new AddressKey("1");
                Address address = Address.newBuilder()
                        .setCorrelationID("1")
                        .setPayloadURI("localhost")
                        .setEventDateTime(1L)
                        .setPublishedDateTime(2L)
                        .build();

                template.executeInTransaction(kafkaOperations -> {
                        ListenableFuture<SendResult<AddressKey, Address>> f = kafkaOperations.send(topicName, key, address);
                        AtomicBoolean result = new AtomicBoolean(false);
                        f.addCallback(s -> result.set(true), fc -> result.set(false));

                        return result.get();
                });

        }
}
