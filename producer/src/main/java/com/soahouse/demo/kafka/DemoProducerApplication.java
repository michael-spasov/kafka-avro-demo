package com.soahouse.demo.kafka;

import com.soahouse.demo.kafka.config.KafkaProducerProperties;
import com.soahouse.demo.kafka.services.ProducerService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ KafkaProducerProperties.class })
public class DemoProducerApplication implements ApplicationRunner {

        private final ProducerService producerService;

        public DemoProducerApplication(ProducerService producerService) {
                this.producerService = producerService;
        }

        public static void main(String[] args) {
                SpringApplication.run(DemoProducerApplication.class, args);
        }

        @Override public void run(ApplicationArguments args) {

                for (int i = 0; i < 10; ++i)
                        producerService.sendMessage();
        }
}
