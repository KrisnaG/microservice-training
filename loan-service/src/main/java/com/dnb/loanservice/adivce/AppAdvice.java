package com.dnb.loanservice.adivce;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dnb.loanservice.exceptions.IdNotFoundException;

@ControllerAdvice
public class AppAdvice {

	public ResponseEntity<?> createMessage(Exception e, HttpStatus status) {
		Map<String, String> map = new HashMap<>();
		map.put("message", e.getMessage());
		map.put("HttpStatus", status + "");

		return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> idNotFoundExceptionHandler(IdNotFoundException e) {
		return createMessage(e, HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, String>> handleException(HttpRequestMethodNotSupportedException e)
			throws IOException {

		String provided = e.getMethod();
		List<String> supported = List.of(e.getSupportedMethods());

		String error = provided + " is not one of the supported Http Methods (" + String.join(",", supported) + ")";

		Map<String, String> errorResponse = Map.of("error", error, "message", e.getLocalizedMessage(), "status",
				HttpStatus.METHOD_NOT_ALLOWED.toString());

		return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);

	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Map<String, String>> handleMediaException(HttpMediaTypeNotSupportedException e)
			throws IOException {

		MediaType provided = e.getContentType();
		List<MediaType> supported = e.getSupportedMediaTypes();

		String error = provided + " is not one of the supported Http Methods (" + supported + ")";

		Map<String, String> errorResponse = Map.of("error", error, "message", e.getMessage(), "status",
				HttpStatus.METHOD_NOT_ALLOWED.toString());

		return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);

	}

}
