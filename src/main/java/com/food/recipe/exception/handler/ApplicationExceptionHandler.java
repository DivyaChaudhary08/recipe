package com.food.recipe.exception.handler;

import java.sql.SQLException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.food.recipe.exception.ApiErrorResponse;
import com.food.recipe.exception.DataNotFoundException;
import com.food.recipe.exception.IncorectFilterSearchCriteria;
import com.food.recipe.exception.InvalidInputException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleException(DataNotFoundException ex) {
		ApiErrorResponse message = new ApiErrorResponse("Data Not Present", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(IncorectFilterSearchCriteria.class)
	public ResponseEntity<ApiErrorResponse> incorectFilterSearchCriteria(IncorectFilterSearchCriteria ex) {
		ApiErrorResponse message = new ApiErrorResponse("Query string is not as per schema", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ApiErrorResponse> handleExceptionInvalidInput(InvalidInputException ex) {
		ApiErrorResponse message = new ApiErrorResponse("Input is null", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@Override
	   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorResponse message = new ApiErrorResponse("Input is incorrect", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<ApiErrorResponse> handleException(MethodArgumentTypeMismatchException ex) {
		ApiErrorResponse message = new ApiErrorResponse("Entered id must be in long", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(SQLException.class)
	public ResponseEntity<Object> handleSQLException(SQLException ex){
    	ApiErrorResponse message = new ApiErrorResponse("SQL EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ApiErrorResponse message = new ApiErrorResponse("Incorrect filter search type", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
