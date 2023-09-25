package com.thehyundai.thepet.kafka;

import com.thehyundai.thepet.global.timetrace.AopControllerVO;
import com.thehyundai.thepet.global.timetrace.AopServiceVO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
//
//    @Bean
//    public ProducerFactory<String, AopServiceVO> msgProducerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Spring Kafka의 JsonSerializer 사용
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//    @Bean
//    public KafkaTemplate<String, AopServiceVO> msgKafkaTemplate() {
//        return new KafkaTemplate<String, AopServiceVO>(msgProducerFactory());
//    }

    @Bean
    public ConsumerFactory<String, AopServiceVO> serviceLogConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "foo");

        JsonDeserializer<AopServiceVO> deserializer = new JsonDeserializer<>(AopServiceVO.class);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AopServiceVO> serviceLogKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AopServiceVO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(serviceLogConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, AopControllerVO> controllerLogConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "foo");

        JsonDeserializer<AopControllerVO> deserializer = new JsonDeserializer<>(AopControllerVO.class);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AopControllerVO> controllerLogKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AopControllerVO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(controllerLogConsumerFactory());
        return factory;
    }


}