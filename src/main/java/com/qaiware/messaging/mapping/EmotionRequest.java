package com.qaiware.messaging.mapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmotionRequest {

    @Size(min = 2, max = 10)
    @Pattern(regexp = "^[^0-9]+$")
    private String payload;
}
