package ru.samarina.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder

public class Response {
    private String uid;
    private String operationUid;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime systemTime;

    private String code;
    private String errorCode;
    private String errorMessage;
}
