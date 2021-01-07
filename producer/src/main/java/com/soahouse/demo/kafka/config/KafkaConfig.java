package com.soahouse.demo.kafka.config;


import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

        private final KafkaProperties kafkaProperties;
        private final KafkaProducerProperties kafkaProducerProperties;
        @Value("${demo.topic-name:topicName}")
        String topicName;

        public KafkaConfig(KafkaProperties kafkaProperties, KafkaProducerProperties kafkaProducerProperties) {
                this.kafkaProperties = kafkaProperties;
                this.kafkaProducerProperties = kafkaProducerProperties;
        }

        @Bean
        public NewTopic adviceTopic() {
                return new NewTopic(topicName, 3, (short) 1);
        }

        @Bean
        public Map<String, Object> producerConfigs() {

                Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());

                props.put("schema.registry.url", kafkaProducerProperties.getSchemaRegistryUrl());

                props.put("key.subject.name.strategy", kafkaProducerProperties.getKeySubjectNameStrategy());
                props.put("value.subject.name.strategy", kafkaProducerProperties.getValueSubjectNameStrategy());

                props.put("auto.register.schemas", kafkaProducerProperties.getAutoRegisterSchemas());
                props.put("use.latest.version", kafkaProducerProperties.getUseLatestVersion());

                props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
                props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "test-transactional-id");

                return props;
        }

        @Bean
        public ProducerFactory<GenericRecord, GenericRecord> producerFactory() {
                return new DefaultKafkaProducerFactory<>(producerConfigs());
        }

        @Bean
        public KafkaTemplate<GenericRecord, GenericRecord> kafkaTemplate() {
                return new KafkaTemplate<>(producerFactory());
        }

}
