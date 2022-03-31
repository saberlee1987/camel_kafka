package com.saber.camel_kafka_producer.routes;

import com.saber.camel_kafka_producer.dto.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;

//@Component
public class KafkaTimerProducerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {


        from("timer:kafka_timer?fixedRate=true&period=30000")
//                .setBody(constant("Hello From camel kafka"))
                .process(exchange -> {
                    Person person = new Person();
                    person.setFirstName("saber");
                    person.setLastName("Azizi");
                    person.setAge(34);
                    person.setNationalCode("0079028748");
                    person.setEmail("saber66@yahoo.com");
                    person.setMobile("09365627895");
                    exchange.getIn().setBody(person);
                })
                .marshal().json(JsonLibrary.Jackson,Person.class)
                .setHeader(KafkaConstants.TOPIC, constant("{{service.kafka.topic}}"))
                .to("kafka:{{service.kafka.topic}}?brokers={{service.kafka.brokers}}")
                .log("Send message ${in.body} to kafka {{service.kafka.brokers}}");


    }
}
