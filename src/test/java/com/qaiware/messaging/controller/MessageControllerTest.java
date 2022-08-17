package com.qaiware.messaging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaiware.messaging.mapping.TextRequest;
import com.qaiware.messaging.model.Message;
import com.qaiware.messaging.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    private static final String EMPTY_STRING = "";
    private static final String MORE_THAN_160_SYMBOL_TEXT = "test characters to move across the 160 "
            + "symbols boundary and see if a validation error is going to be thrown or rather nothing will "
            + "happen which will prove that a better implementation is needed";
    private static final String VALID_TEXT = "Simple text mess@ge t0 test the functional!ty : )";

    private static final String MORE_THAN_10_SYMBOL_EMOTION = "happy, Happy, sad, excited";
    private static final String INVALID_EMOTION = "Excited1";
    private static final String VALID_EMOTION = "H@ppy : )";

    private static final String SEND_TEXT_ENDPOINT = "/messages/send_text";
    private static final String SEND_EMOTION_ENDPOINT = "/messages/send_emotion";
    private static final String GET_RESPONSE_BODY_SYMBOL = "$";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void whenTextPayloadLengthIsZeroThenReturnPreconditionFailedStatus() throws Exception {
        //Given
        TextRequest request = TextRequest.builder().payload(EMPTY_STRING).build();
        //When-Then
        this.mockMvc.perform(post(SEND_TEXT_ENDPOINT)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath(GET_RESPONSE_BODY_SYMBOL).doesNotExist());
    }

    @Test
    public void whenTextPayloadLengthIsBiggerThan160ThenReturnPreconditionFailedStatus() throws Exception {
        //Given
        TextRequest request = TextRequest.builder().payload(MORE_THAN_160_SYMBOL_TEXT).build();
        //When-Then
        this.mockMvc.perform(post(SEND_TEXT_ENDPOINT)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath(GET_RESPONSE_BODY_SYMBOL).doesNotExist());
    }

    @Test
    public void whenTextPayloadIsValidThenReturnCreatedStatus() throws Exception {
        //Given
        when(messageService.save(any(), any())).thenReturn(Message.builder().build());
        TextRequest request = TextRequest.builder().payload(VALID_TEXT).build();
        //When-Then
        this.mockMvc.perform(post(SEND_TEXT_ENDPOINT)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(GET_RESPONSE_BODY_SYMBOL).doesNotExist());
    }

    @Test
    public void whenEmotionPayloadIsEmptyThenReturnPreconditionFailedStatus() throws Exception {
        //Given
        when(messageService.save(any(), any())).thenReturn(Message.builder().build());
        TextRequest request = TextRequest.builder().payload(EMPTY_STRING).build();
        //When-Then
        this.mockMvc.perform(post(SEND_EMOTION_ENDPOINT)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath(GET_RESPONSE_BODY_SYMBOL).doesNotExist());
    }

    @Test
    public void whenEmotionPayloadIsIsBiggerThan10ThenReturnPreconditionFailedStatus() throws Exception {
        //Given
        when(messageService.save(any(), any())).thenReturn(Message.builder().build());
        TextRequest request = TextRequest.builder().payload(MORE_THAN_10_SYMBOL_EMOTION).build();
        //When-Then
        this.mockMvc.perform(post(SEND_EMOTION_ENDPOINT)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath(GET_RESPONSE_BODY_SYMBOL).doesNotExist());
    }

    @Test
    public void whenEmotionPayloadIsContainsForbiddenCharacterThenReturnPreconditionFailedStatus() throws Exception {
        //Given
        when(messageService.save(any(), any())).thenReturn(Message.builder().build());
        TextRequest request = TextRequest.builder().payload(INVALID_EMOTION).build();
        //When-Then
        this.mockMvc.perform(post(SEND_EMOTION_ENDPOINT)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath(GET_RESPONSE_BODY_SYMBOL).doesNotExist());
    }

    @Test
    public void whenEmotionPayloadIsValidThenReturnCreatedStatus() throws Exception {
        //Given
        when(messageService.save(any(), any())).thenReturn(Message.builder().build());
        TextRequest request = TextRequest.builder().payload(VALID_EMOTION).build();
        //When-Then
        this.mockMvc.perform(post(SEND_EMOTION_ENDPOINT)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(GET_RESPONSE_BODY_SYMBOL).doesNotExist());
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}