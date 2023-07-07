package adt.demo.rewardprogram.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String EXCEPTION_LOCALIZED_MESSAGE = "Exception-Localized-Message";
	private static final String TRACE_ID = "Trace-Id";
	private static final String EXCEPTION_CLASS = "Exception-Class";
	private static final String CUSTEMOR_ID = "customerId";

	@ExceptionHandler(CustomerNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleAllExceptions(CustomerNotFoundException ex) {

		Map<String, String> details = new HashMap<>();

		details.put(EXCEPTION_CLASS, CustomerNotFoundException.class.getName());
		details.put(EXCEPTION_LOCALIZED_MESSAGE, ex.getLocalizedMessage());
		details.put(TRACE_ID, ex.getTraceId());
		details.put(CUSTEMOR_ID, ex.getCustomerId().toString());

		ErrorResponse error = ErrorResponse.builder().details(details)
				.message("Please check thebelow details and try again. Thank you").build();

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}