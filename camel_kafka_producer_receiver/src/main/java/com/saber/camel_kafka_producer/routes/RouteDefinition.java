package com.saber.camel_kafka_producer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RouteDefinition extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .contextPath("{{service.api.base-path}}")
                .apiContextPath("{{service.api.swagger-path}}")
                .enableCORS(true)
                .apiProperty("api.title", "{{service.api.swagger-title}}")
                .apiProperty("api.version", "{{service.api.swagger-version}}")
                .apiProperty("cors", "true")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");
    }
}
