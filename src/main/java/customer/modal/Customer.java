package customer.modal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	private Integer quarterlyRewardsCollected;
	private Integer perMonthRewardsAverage;
	private String customerId;
	private List<TransactionDtls> transDtlsList; 


}
