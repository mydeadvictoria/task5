package com.mdv.task5.api.transformer;

import com.mdv.task5.api.dto.PersonDto;
import com.mdv.task5.proto.PersonProto;

public class PersonTransformer {
    public static PersonProto.Person transform(PersonDto personDto) {
        return PersonProto.Person.newBuilder()
                .setFirstName(personDto.getFirstName())
                .setLastName(personDto.getLastName())
                .build();
    }
}
