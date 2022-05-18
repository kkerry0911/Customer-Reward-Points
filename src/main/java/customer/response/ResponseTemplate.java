package customer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseTemplate {

	private String customerId;
	private Integer rewardPointsForThreeMonthsDuration;
	private Integer perMonthRewardsAverage;

}
