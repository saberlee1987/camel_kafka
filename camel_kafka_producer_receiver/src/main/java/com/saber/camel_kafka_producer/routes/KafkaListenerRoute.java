package com.saber.camel_kafka_producer.routes;

import com.saber.camel_kafka_producer.dto.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("kafka:{{service.kafka.topic}}?brokers={{service.kafka.brokers}}")
                .log("message from kafka ===> ${in.body}")
                .unmarshal().json(JsonLibrary.Jackson, Person.class)
                .log("Response ===> ${in.body}");
    }
}
