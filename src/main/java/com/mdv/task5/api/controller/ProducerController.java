package com.mdv.task5.api.controller;

import com.mdv.task5.api.dto.PersonDto;
import com.mdv.task5.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producer")
public class ProducerController {
    private final ProducerService producerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postRecord(@RequestBody PersonDto personDto) {
        producerService.pushPerson(personDto.getFirstName(), personDto.getLastName());
        return ResponseEntity.ok().build();
    }
}
