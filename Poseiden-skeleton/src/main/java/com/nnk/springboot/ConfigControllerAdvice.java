package com.nnk.springboot;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 
 * Management exception and timestamp custom editor for date format
 * 
 * @author trimok
 * 
 */
@ControllerAdvice
public class ConfigControllerAdvice {
    /**
     * Registering the SqlTimestampPropertyEditor
     * 
     * @param binder registering timestamp custom editor
     */
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
	binder.registerCustomEditor(Timestamp.class, new SqlTimestampPropertyEditor("yyyy-MM-dd'T'HH:mm"));
    }

    /**
     * Exception Management
     * 
     * @author trimok
     *
     */
    @ControllerAdvice
    public class MyExceptionHandlers {
	/**
	 * handle
	 * 
	 * @param ex : IllegalArgumentException
	 * @return : ResponseEntity IllegalArgumentException
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<IllegalArgumentException> handle(IllegalArgumentException ex) {
	    return new ResponseEntity<IllegalArgumentException>(ex, HttpStatus.BAD_REQUEST);
	}
    }
}