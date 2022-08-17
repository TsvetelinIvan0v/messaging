package com.qaiware.messaging.controller;

import com.qaiware.messaging.mapping.EmotionRequest;
import com.qaiware.messaging.mapping.TextRequest;
import com.qaiware.messaging.model.MessageTypeEnum;
import com.qaiware.messaging.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/send_text")
    @Valid
    public ResponseEntity<Void> sendText(@Valid @RequestBody final TextRequest request) {
        messageService.save(request.getPayload(), MessageTypeEnum.TEXT);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/send_emotion")
    public ResponseEntity<Void> sendEmotion(@Valid @RequestBody final EmotionRequest request) {
        messageService.save(request.getPayload(), MessageTypeEnum.EMOTION);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
