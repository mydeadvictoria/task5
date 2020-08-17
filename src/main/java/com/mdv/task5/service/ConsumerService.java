package com.mdv.task5.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.mdv.task5.proto.PersonProto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {

    private final Parser<PersonProto.Person> personParser = PersonProto.Person.parser();

    @KafkaListener(topics = "test", groupId = "test_group")
    public void consume(byte[] personBytes) throws InvalidProtocolBufferException {
        PersonProto.Person person = personParser.parseFrom(personBytes);

        log.info("firstName: " + person.getFirstName());
        log.info("lastName: " + person.getLastName() + "\n");
    }
}
