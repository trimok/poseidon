package com.nnk.springboot;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ConfigControllerAdvice {
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
	binder.registerCustomEditor(Timestamp.class, new SqlTimestampPropertyEditor("yyyy-MM-dd'T'HH:mm"));
    }

    @ControllerAdvice
    public class MyExceptionHandlers {
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<IllegalArgumentException> handle(IllegalArgumentException ex) {
	    return new ResponseEntity<IllegalArgumentException>(ex, HttpStatus.BAD_REQUEST);
	}
    }
}