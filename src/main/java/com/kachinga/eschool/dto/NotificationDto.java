package com.kachinga.eschool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kachinga.eschool.entity.Notification;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto implements Serializable {
    private Long id;

    private Long recipientId;

    private String title;

    private String message;

    private Boolean read;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime readAt;

    public NotificationDto(Notification notification) {
        BeanUtils.copyProperties(notification, this);
    }
}
