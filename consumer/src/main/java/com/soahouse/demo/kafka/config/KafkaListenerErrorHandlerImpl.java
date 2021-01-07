package com.soahouse.demo.kafka.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;

public class KafkaListenerErrorHandlerImpl implements KafkaListenerErrorHandler {

        @Override public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
                return null;
        }

        @Override public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
                return null;
        }
}
