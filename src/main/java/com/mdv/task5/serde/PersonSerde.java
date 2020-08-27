package com.mdv.task5.serde;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.mdv.task5.proto.PersonProto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class PersonSerde implements Serializer<PersonProto.Person>,
        Deserializer<PersonProto.Person>, Serde<PersonProto.Person> {

    private final Parser<PersonProto.Person> personParser = PersonProto.Person.parser();

    @Override
    public Serializer<PersonProto.Person> serializer() {
        return this;
    }

    @Override
    public Deserializer<PersonProto.Person> deserializer() {
        return this;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, PersonProto.Person data) {
        return data.toByteArray();
    }

    @Override
    public byte[] serialize(String topic, Headers headers, PersonProto.Person data) {
        return this.serialize(topic, data);
    }

    @Override
    public PersonProto.Person deserialize(String topic, Headers headers, byte[] data) {
        return this.deserialize(topic, data);
    }

    @Override
    public PersonProto.Person deserialize(String topic, byte[] data) {
        try {
            return personParser.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public void close() {

    }
}
