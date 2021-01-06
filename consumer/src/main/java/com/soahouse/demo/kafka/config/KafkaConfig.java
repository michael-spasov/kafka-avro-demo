package com.soahouse.demo.kafka.config;

import gov.dwp.citizen.address.Address;
import gov.dwp.citizen.address.AddressKey;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

        private final KafkaProperties kafkaProperties;

        public KafkaConfig(KafkaProperties kafkaProperties) {
                this.kafkaProperties = kafkaProperties;
        }

        @Bean
        public NewTopic adviceTopic() {
                return new NewTopic("topicName", 3, (short) 1);
        }

        @Bean
        public Map<String, Object> consumerConfigs() {
                Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());

                props.put("schema.registry.url", "http://127.0.0.1:8081");
                props.put("specific.avro.reader", true);

                props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
                props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
                props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, io.confluent.kafka.serializers.KafkaAvroDeserializer.class);

                return props;
        }

        @Bean
        public ConsumerFactory<AddressKey, Address> consumerFactory() {
                return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<AddressKey, Address> kafkaListenerContainerFactory() {
                ConcurrentKafkaListenerContainerFactory<AddressKey, Address> factory = new ConcurrentKafkaListenerContainerFactory<>();
                factory.setConsumerFactory(consumerFactory());
                factory.setErrorHandler(new KafkaErrorHandler());
                return factory;
        }
}
