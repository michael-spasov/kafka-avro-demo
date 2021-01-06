package com.soahouse.demo.kafka;

import com.soahouse.demo.kafka.config.KafkaConsumerProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ KafkaConsumerProperties.class })
public class DemoConsumerApplication implements ApplicationRunner {

        public static void main(String[] args) {
                SpringApplication.run(DemoConsumerApplication.class, args);
        }

        @Override public void run(ApplicationArguments args) throws Exception {

        }
}
