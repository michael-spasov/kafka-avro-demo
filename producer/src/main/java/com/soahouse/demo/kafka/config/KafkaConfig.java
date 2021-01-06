package com.soahouse.demo.kafka.config;

import gov.dwp.citizen.address.Address;
import gov.dwp.citizen.address.AddressKey;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

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
        public Map<String, Object> producerConfigs() {

                Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());

                props.put("schema.registry.url", "http://127.0.0.1:8081");

                props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
                props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "test-transactional-id");

                return props;
        }

        @Bean
        public ProducerFactory<AddressKey, Address> producerFactory() {
                return new DefaultKafkaProducerFactory<>(producerConfigs());
        }

        @Bean
        public KafkaTemplate<AddressKey, Address> kafkaTemplate() {
                return new KafkaTemplate<>(producerFactory());
        }

}
