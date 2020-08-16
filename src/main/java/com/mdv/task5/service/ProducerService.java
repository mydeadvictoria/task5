package com.mdv.task5.service;

import com.mdv.task5.proto.PersonProto;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Service
public class ProducerService {
    private final KafkaProducer<String, byte[]> producer;

    @Value("${kafka.producer.topic-name}")
    private String topicName;

    public ProducerService(
            @Value("${kafka.producer.bootstrap-servers}") String bootstrapServers
    ) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        producer = new KafkaProducer<>(properties);
    }

//    public void pushRecord(String value) {
//        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, value);
//        producer.send(record);
//    }

    public void pushPerson(String firstName, String lastName) {
        PersonProto.Person person = PersonProto.Person.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();

        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topicName, person.toByteArray());
        producer.send(record);
    }

    @PreDestroy
    private void destroy() {
        producer.close();
    }
}
