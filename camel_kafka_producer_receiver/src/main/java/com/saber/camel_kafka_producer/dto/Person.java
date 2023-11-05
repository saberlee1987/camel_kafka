package com.saber.camel_kafka_producer.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Person {
    @ApiModelProperty(example = "saber")
    private String firstName;
    @ApiModelProperty(example = "azizi")
    private String lastName;
    @ApiModelProperty(example = "0079028748")
    private String nationalCode;
    @ApiModelProperty(example = "36")
    private Integer age;
    @ApiModelProperty(example = "saberazizi66@yahoo.com")
    private String email;
    @ApiModelProperty(example = "09365627895")
    private String mobile;

    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, Person.class);
    }
}