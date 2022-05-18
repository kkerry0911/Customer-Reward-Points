package customer.exceptionHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4008462812894355047L;
	
	public CustomerNotFoundException(String message) {
		super(message);
	}

}
