//package com.example.j_booking.configuration.kafka;
//
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Properties;
//
//@Configuration
//public class KafkaConsumerConfig {
//    @Bean
//    public KafkaConsumer<String, String> kafkaConsumer() {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
//        props.put("group.id", "my-group");
//        props.put("auto.offset.reset", "earliest");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        return new KafkaConsumer<>(props);
//    }
//}
