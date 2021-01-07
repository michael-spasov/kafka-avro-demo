package com.soahouse.demo.kafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.avro.producer")
@Getter
@Setter
public class KafkaProducerProperties {
        private String schemaRegistryUrl;
        private Class<?> keySubjectNameStrategy = io.confluent.kafka.serializers.subject.TopicNameStrategy.class;
        private Class<?> valueSubjectNameStrategy = io.confluent.kafka.serializers.subject.TopicNameStrategy.class;

        private Boolean autoRegisterSchemas = true;
        private Boolean useLatestVersion = true;
}
