package customer.modal;

import java.time.LocalDateTime;

import customer.enums.CurrencyDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDtls {

	private String transactionId;
	private Double spentAmount;
	private CurrencyDetails currency;
	// will be used to calculate the 3 months period
	private LocalDateTime transactionDate;
	private Boolean isTransactionAlreadyRewarded;
}
