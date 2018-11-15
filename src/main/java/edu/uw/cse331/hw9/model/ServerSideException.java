package edu.uw.cse331.hw9.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerSideException extends Exception {

    public ServerSideException(String message) {
        super(message);
    }

}
