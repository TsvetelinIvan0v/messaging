package com.qaiware.messaging.service;

import com.qaiware.messaging.model.Message;
import com.qaiware.messaging.model.MessageTypeEnum;
import com.qaiware.messaging.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    private static final String TEXT_PAYLOAD = "testing the text payload";
    private static final String EMOTION_PAYLOAD = "happy";
    private static final String NULL_OBJECT = "Initialized object is expected";
    private static final String UNEXPECTED_PAYLOAD = "Payload is not as expected";
    private static final String UNEXPECTED_TYPE = "Message type is not as expected";

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepository;

    @Captor
    private ArgumentCaptor<Message> messageCaptor;

    @Test
    public void whenValidPayloadOfTypeTextIsPassedThenSaveAsMessageEntityOfTypeText() {
        //When
        messageService.save(TEXT_PAYLOAD, MessageTypeEnum.TEXT);
        Mockito.verify(messageRepository).save(messageCaptor.capture());
        Message message = messageCaptor.getValue();
        //Then
        assertNotNull(message, NULL_OBJECT);
        assertEquals(TEXT_PAYLOAD, message.getPayload(), UNEXPECTED_PAYLOAD);
        assertEquals(MessageTypeEnum.TEXT, message.getType(), UNEXPECTED_TYPE);
    }

    @Test
    public void whenValidPayloadOfTypeEmotionIsPassedThenSaveAsMessageEntityOfTypeEmotion() {
        //When
        messageService.save(EMOTION_PAYLOAD, MessageTypeEnum.EMOTION);
        Mockito.verify(messageRepository).save(messageCaptor.capture());
        Message message = messageCaptor.getValue();
        //Then
        assertNotNull(message, NULL_OBJECT);
        assertEquals(EMOTION_PAYLOAD, message.getPayload(), UNEXPECTED_PAYLOAD);
        assertEquals(MessageTypeEnum.EMOTION, message.getType(), UNEXPECTED_TYPE);
    }
}