package org.example.taskapi.infra;

import org.example.taskapi.infra.exceptions.BaseException;
import org.example.taskapi.infra.exceptions.TaskStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(TaskStatusException.class)
    private ResponseEntity<RestErrorMessage> buildResponseEntity(TaskStatusException taskStatusException) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                taskStatusException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(BaseException.class)
    public final ResponseEntity<Object> handleBaseException(final BaseException ex) {
        logger.error(ex.getErrorMessage(), ex);

        Fault exceptionResponse = Fault.builder()
                .code(ex.getErrorCodeEnum())
                .message(ex.getErrorMessage())
                .details(ex.getMessage())
                .build();

        return ResponseEntity.status(ex.getHttpStatus()).body(exceptionResponse);
    }

}
