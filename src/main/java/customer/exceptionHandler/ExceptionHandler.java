package customer.exceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandler {

	private final static String TIMESTAMP = "timestamp";
	private final static String ERRORS = "errors";

	@org.springframework.web.bind.annotation.ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Object> resourceNotFoundException(CustomerNotFoundException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put(ERRORS, ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

	}

	@org.springframework.web.bind.annotation.ExceptionHandler(RewardsNotApplicableException.class)
	public ResponseEntity<Object> resourceNotFoundException(RewardsNotApplicableException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put(ERRORS, ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);

	}

}
