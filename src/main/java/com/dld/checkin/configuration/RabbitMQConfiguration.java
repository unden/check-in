package com.dld.checkin.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public Queue declareQueue4CheckIn() {
        return new Queue("check-in");
    }

    @Bean
    public Queue declareQueue4Point() {
        return new Queue("point");
    }

    @Bean
    public FanoutExchange declareExchange1() {
        return new FanoutExchange("check-in-topic");
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(declareQueue4CheckIn())
                .to(declareExchange1());
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(declareQueue4Point())
                .to(declareExchange1());
    }

}
