package io.mngt.consumeramqp;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private ConnectionFactory factory = new ConnectionFactory();
    private Channel channel;
    private Connection connection;

    @Override
    public void run(String... args) throws IOException, TimeoutException {
        factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
        channel.queueDeclare("test_queue", false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, 
                byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume("test_queue", true, consumer);

    }

    

    
}