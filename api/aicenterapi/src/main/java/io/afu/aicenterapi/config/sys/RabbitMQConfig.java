package io.afu.aicenterapi.config.sys;

import jakarta.annotation.Resource;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {


    public static final String TASK_EXCHANGE = "taskexchange";


    public static final String TASK_QUEUE = "taskqueue";


    public static final String RESULT_QUEUE = "resultqueue";

    @Resource
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }


    @Bean
    public Queue taskQueue() {
        return new Queue(TASK_QUEUE,true);
    }

    @Bean
    public DirectExchange taskExchange() {
        return new DirectExchange(TASK_EXCHANGE,true,false);
    }

    @Bean
    public Binding bindingDirectExchange() {
        return BindingBuilder.bind(taskQueue()).to(taskExchange()).with(RESULT_QUEUE);
    }















}
