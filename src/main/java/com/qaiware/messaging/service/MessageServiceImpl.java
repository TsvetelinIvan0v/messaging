package com.qaiware.messaging.service;

import com.qaiware.messaging.model.Message;
import com.qaiware.messaging.model.MessageTypeEnum;
import com.qaiware.messaging.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message save(final String payload, final MessageTypeEnum type) {
        return messageRepository.save(Message.builder().type(type).payload(payload).createdAt(new Date()).build());
    }
}
