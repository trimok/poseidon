package com.nnk.springboot;

import java.sql.Timestamp;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ConfigControllerAdvice {
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
	binder.registerCustomEditor(Timestamp.class, new SqlTimestampPropertyEditor("yyyy-MM-dd'T'HH:mm"));
    }
}