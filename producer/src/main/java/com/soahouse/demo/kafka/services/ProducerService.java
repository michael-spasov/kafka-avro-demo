package com.soahouse.demo.kafka.services;

import gov.dwp.citizen.address.Address;
import gov.dwp.citizen.address.AddressKey;
import gov.dwp.citizen.death.DeathDateUpdated;
import gov.dwp.citizen.death.DeathKey;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ProducerService {

        final KafkaTemplate<GenericRecord, GenericRecord> template;

        @Value("${demo.topic-name:topicName}")
        private String topicName;

        public ProducerService(
                KafkaTemplate<GenericRecord, GenericRecord> template) {
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

                sendDataInTransaction(key, address);

                DeathDateUpdated deathDateUpdated = DeathDateUpdated.newBuilder()
                        .setEventDateTime(1L)
                        .setPublishedDateTime(2L)
                        .setCorrelationID("12")
                        .setPayloadURI("localhost")
                        .setSubjectIdentifier("subject")
                        .build();
                DeathKey dKey = DeathKey.newBuilder()
                        .setKey("12")
                        .build();

                sendDataInTransaction(dKey, deathDateUpdated);
        }

        private void sendDataInTransaction(GenericRecord key, GenericRecord address) {
                template.executeInTransaction(kafkaOperations -> {
                        ListenableFuture<SendResult<GenericRecord, GenericRecord>> f = kafkaOperations.send(topicName, key, address);
                        AtomicBoolean result = new AtomicBoolean(false);
                        f.addCallback(new SuccessCallback<SendResult<GenericRecord, GenericRecord>>() {
                                @Override public void onSuccess(SendResult<GenericRecord, GenericRecord> s) {
                                        result.set(true);
                                }
                        }, new FailureCallback() {
                                @Override public void onFailure(Throwable fc) {

                                        result.set(false);
                                }
                        });

                        return result.get();
                });
        }
}
