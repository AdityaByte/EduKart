package com.edukart.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String destination; // This could be the email of the user.
    private String subject; // The subject is also passed by the other service.
    private String message; // The message that we want to send.
}
