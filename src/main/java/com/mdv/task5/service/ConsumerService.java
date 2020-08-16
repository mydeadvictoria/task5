package com.mdv.task5.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.mdv.task5.proto.PersonProto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
@Service
public class ConsumerService {

    private final KafkaConsumer<String, byte[]> consumer;
    private final Parser<PersonProto.Person> personParser;

    public ConsumerService(
            @Value("${kafka.consumer.bootstrap-servers}") String bootstrapServers,
            @Value("${kafka.consumer.group-id}") String groupId,
            @Value("${kafka.consumer.topic-name}") String topicName
    ) {
        personParser = PersonProto.Person.parser();

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topicName));
    }

    public void poll() {
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));
            records.forEach((record) -> {
                try {
                    PersonProto.Person person = personParser.parseFrom(record.value());
                    log.info("firstName: " + person.getFirstName());
                    log.info("lastName: " + person.getLastName() + "\n");
                } catch (InvalidProtocolBufferException e) {
                    log.error(e.toString());
                }
            });
        }
    }
}
