package org.example.taskapi.infra;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.taskapi.domain.enums.ErrorCodeEnum;

@Getter
@Setter
@Builder
public class Fault {
    private ErrorCodeEnum code;
    private String message;
    private String details;
}

