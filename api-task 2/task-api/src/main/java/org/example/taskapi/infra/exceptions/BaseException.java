package org.example.taskapi.infra.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskapi.constants.ErrorMessages;
import org.example.taskapi.domain.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private ErrorCodeEnum errorCodeEnum;
    private ErrorMessages errorMessages;

    public BaseException(
            final ErrorCodeEnum errorCodeEnum,
            final ErrorMessages errorMessages,
            final String message
    ) {
        super(message);
        this.errorCodeEnum = errorCodeEnum;
        this.errorMessages = errorMessages;
    }

    public String getErrorMessage() {
        return errorMessages.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return errorMessages.getHttpStatus();
    }
}
