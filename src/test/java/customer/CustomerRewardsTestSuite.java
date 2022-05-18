package customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import customer.enums.CurrencyDetails;
import customer.modal.Customer;
import customer.modal.TransactionDtls;
import customer.service.RewardsCalculatorSvcImpl;

public class CustomerRewardsTestSuite {

	private RewardsCalculatorSvcImpl rewardsCalculatorSvcImpl;

	@BeforeEach
	public void setup() {
		rewardsCalculatorSvcImpl = new RewardsCalculatorSvcImpl();

	}

	static Stream<List<Customer>> getMockData() {

		/**
		 * few are already awarded and non awarded one is less than 50$
		 */
		TransactionDtls t1c4 = new TransactionDtls("8", 1000.0, CurrencyDetails.USD,
				LocalDate.of(2021, 4, 4).atStartOfDay(), true);
		TransactionDtls t2c4 = new TransactionDtls("9", 49.0, CurrencyDetails.USD,
				LocalDate.of(2021, 4, 4).atStartOfDay(), false);

		Customer c1 = new Customer(0, 0, "C1", Arrays.asList(t1c4, t2c4));

		return Stream.of(Arrays.asList(c1));
	}

	@MethodSource("getMockData")
	@ParameterizedTest
	void assertRewards(List<Customer> customerData) {
		assertEquals(0, rewardsCalculatorSvcImpl.getRewardsEarned(customerData.get(0).getCustomerId(), customerData));
	}
}
