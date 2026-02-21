//package com.example.j_booking.configuration.kafka;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.Properties;
//
//@Configuration
//@RequiredArgsConstructor
//public class KafkaProducerConfig {
//    KafkaTemplate<String, String> kafkaTemplate;
//
////    @Bean
////    public ProducerFactory<String, String> producerFactory() {
////        return new DefaultKafkaProducerFactory<>(kafkaProducerProperties(), new StringSerializer(), )
////    }
//
//    @Bean
//    public KafkaProducer<String, String> kafkaProducerProperties() {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
//        props.put("acks", "all");
//        props.put("retries", 5);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        return new KafkaProducer<>(props);
//    }
//}
