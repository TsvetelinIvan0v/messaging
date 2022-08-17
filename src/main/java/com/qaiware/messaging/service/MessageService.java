package com.qaiware.messaging.service;

import com.qaiware.messaging.model.Message;
import com.qaiware.messaging.model.MessageTypeEnum;

public interface MessageService {

    Message save(final String payload, final MessageTypeEnum type);
}
