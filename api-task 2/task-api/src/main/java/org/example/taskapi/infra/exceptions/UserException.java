package org.example.taskapi.infra.exceptions;

import org.example.taskapi.constants.ErrorMessages;
import org.example.taskapi.domain.enums.ErrorCodeEnum;

public class UserException extends BaseException {


    public UserException(
            ErrorCodeEnum errorCodeEnum,
            ErrorMessages errorMessages,
            String message) {
        super(errorCodeEnum, errorMessages, message);
    }
}
