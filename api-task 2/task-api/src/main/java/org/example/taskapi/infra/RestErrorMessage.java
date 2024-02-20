package org.example.taskapi.infra;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class RestErrorMessage {


    private String message;
    private final HttpStatus status;

    public RestErrorMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
