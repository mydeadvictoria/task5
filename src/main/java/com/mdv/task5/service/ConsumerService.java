package com.mdv.task5.service;

import com.mdv.task5.proto.PersonProto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {

    @KafkaListener(topics = "test", groupId = "test_group")
    public void consume(PersonProto.Person person) {
        log.info("firstName: " + person.getFirstName());
        log.info("lastName: " + person.getLastName() + "\n");
    }
}
