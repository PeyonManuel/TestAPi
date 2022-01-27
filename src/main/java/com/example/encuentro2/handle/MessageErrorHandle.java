package com.example.encuentro2.handle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MessageErrorHandle {

    Logger logger = LogManager.getLogger(MessageErrorHandle.class);

    @ResponseBody
    @ExceptionHandler(FirstApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage recursoNoExistenteHandle(FirstApplicationException ex) {
        logger.error(ex);
        return new ErrorMessage(ex.getMessage());
    }

}