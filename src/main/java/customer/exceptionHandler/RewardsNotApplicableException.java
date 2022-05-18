package customer.exceptionHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardsNotApplicableException extends RuntimeException {

	private static final long serialVersionUID = 5708106487178608844L;

	public RewardsNotApplicableException(String message) {
		super(message);
	}
}
