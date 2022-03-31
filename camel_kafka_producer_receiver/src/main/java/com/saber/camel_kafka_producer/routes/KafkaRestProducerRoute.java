package com.saber.camel_kafka_producer.routes;

import com.saber.camel_kafka_producer.dto.*;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.ws.rs.core.MediaType;

@Component
public class KafkaRestProducerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        rest("/person")
                .post("/produce")
                .id(Routes.PERSON_PRODUCE_KAFKA_ROUTE_ID)
                .consumes(MediaType.APPLICATION_JSON)
                .produces(MediaType.APPLICATION_JSON)
                .responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(KafkaProducerResponseDto.class).endResponseMessage()
                .enableCORS(true)
                .bindingMode(RestBindingMode.json)
                .type(Person.class)
                .route()
                .routeId(Routes.PERSON_PRODUCE_KAFKA_ROUTE)
                .routeGroup(Routes.PERSON_PRODUCE_KAFKA_ROUTE_GROUP)
                .to(String.format("direct:%s", Routes.PERSON_PRODUCE_KAFKA_ROUTE_GATEWAY));

        from(String.format("direct:%s", Routes.PERSON_PRODUCE_KAFKA_ROUTE_GATEWAY))
                .routeId(Routes.PERSON_PRODUCE_KAFKA_ROUTE_GATEWAY)
                .routeGroup(Routes.PERSON_PRODUCE_KAFKA_ROUTE_GROUP)
                .log("Request for send message to kafka ====> ${in.body}")
                .marshal().json(JsonLibrary.Jackson, Person.class)
                .to(String.format("direct:%s", Routes.PERSON_PRODUCE_KAFKA_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.PERSON_PRODUCE_KAFKA_ROUTE_GATEWAY_OUT))
                .routeId(Routes.PERSON_PRODUCE_KAFKA_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.PERSON_PRODUCE_KAFKA_ROUTE_GROUP)
                .log("Request for send message to kafka ====> ${in.body}")
                .to("kafka:{{service.kafka.topic}}?brokers={{service.kafka.brokers}}")
                .process(exchange -> {
                    KafkaProducerResponseDto responseDto = new KafkaProducerResponseDto();
                    responseDto.setCode(0);
                    responseDto.setText("your data published successfully");
                    exchange.getIn().setBody(responseDto);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

    }
}
