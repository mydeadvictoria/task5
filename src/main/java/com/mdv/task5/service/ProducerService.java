package com.mdv.task5.service;

import com.mdv.task5.proto.PersonProto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    private final String TOPIC = "test";

    public void sendPerson(PersonProto.Person person) {
        kafkaTemplate.send(TOPIC, person.toByteArray());
    }
}
