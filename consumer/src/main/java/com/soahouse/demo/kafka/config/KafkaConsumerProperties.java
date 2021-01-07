package com.soahouse.demo.kafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.avro.consumer")
@Getter
@Setter
public class KafkaConsumerProperties {

        private String schemaRegistryUrl;
        private Boolean specificReader = false;
        private Class<?> keySubjectNameStrategy = io.confluent.kafka.serializers.subject.TopicNameStrategy.class;
        private Class<?> valueSubjectNameStrategy = io.confluent.kafka.serializers.subject.TopicNameStrategy.class;
}
