package com.exam.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler 
extends ResponseEntityExceptionHandler  
{
    // ResponseEntityExceptionHandler 내부에 logger 기능 포함됨.
	//private Logger logger = LoggerFactory.getLogger(getClass()); 

	// 400 에러에 대한 응답처리 개선
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		 logger.error("logger: Exception 발생: " +  ex.getMessage());  
		ErrorDetails errorDetails = 
				     //  ex.getMessage() 이용하면 에러메시지가 많이 출력됨.
				     //  따라서 ex.getFieldError().getDefaultMessage() 사용하여 개별 에러메시지를 출력하자.
					  new ErrorDetails(LocalDateTime.now(), ex.getFieldError().getDefaultMessage(), request.getDescription(false)); 
					  
			  // 다음 메서드 사용하면 message="에러메시지"에서 설정된 값 얻을 수 있다.
//			  ex.getFieldError().getDefaultMessage()
//		      ex.getFieldErrors()
//			  ex.getErrorCount()

			  return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);

	}
	
	
	
	  @ExceptionHandler(Exception.class)
	    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request){
		  logger.error("logger: Exception 발생: " +  ex.getMessage());

              ErrorDetails errorDetails = 
		  new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false)); 
		  
		  return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

       } 

	  
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		logger.error("logger: UserNotFoundException 발생: " +  ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
				// true 인 경우 출력: "details": "uri=/app/users/9;client=0:0:0:0:0:0:0:1"
				// flase 인 경우 출력: "details": "uri=/app/users/9"
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	
	
	


}

class ErrorDetails {

	private LocalDateTime timestamp;
	private String message;
	private String details;

	public ErrorDetails(LocalDateTime timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
	

}