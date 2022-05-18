package customer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CurrencyDetails {

	USD("USD", "$", " US dollars");

	private String currenyName;
	private String symbol;
	private String description;

}
