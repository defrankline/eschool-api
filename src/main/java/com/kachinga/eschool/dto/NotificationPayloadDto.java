package com.kachinga.eschool.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPayloadDto implements Serializable {
    private Long id;
    private UUID uuid;
    private String title;
    private String message;
    private String sentAt;
}
