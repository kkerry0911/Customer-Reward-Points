package customer.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import customer.enums.CurrencyDetails;
import customer.exceptionHandler.CustomerNotFoundException;
import customer.exceptionHandler.RewardsNotApplicableException;
import customer.modal.Customer;
import customer.modal.TransactionDtls;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RewardsCalculatorSvcImpl implements RewardsCalculatorSvc {

	protected List<Customer> customerDetailsAndTransactionData;

	public RewardsCalculatorSvcImpl() {
		this.customerDetailsAndTransactionData = getMockData();
	}

	@Override
	public Integer getRewardsEarned(String customerId) {
		log.debug("calculating rewards earned");
		List<TransactionDtls> customerTransDtls = this.getCustomerTransactionDetails(customerId);

		Integer rewards = this.getUnrewardedTrnsAmountSpentInThreeMonthsDuration(customerTransDtls);

		log.debug("setting rewards values to monthly averages and quarterly elgibile rewards attributes");
		customerDetailsAndTransactionData.stream()
				.filter(customer -> customer.getCustomerId().equalsIgnoreCase(customerId)).forEach(customer -> {
					customer.setQuarterlyRewardsCollected(rewards);
					customer.setPerMonthRewardsAverage(rewards / 3);
				});

		return rewards;
	}

	public Integer getRewardsEarned(String customerId, List<Customer> customerData) {

		this.customerDetailsAndTransactionData = customerData;
		return getRewardsEarned(customerId);
	}

	private List<TransactionDtls> getCustomerTransactionDetails(String customerId) {
		log.debug("fetching transaction record of customer");

		Optional<List<TransactionDtls>> customerTransDtls = customerDetailsAndTransactionData.stream()
				.filter(customer -> customer.getCustomerId().equalsIgnoreCase(customerId))
				.map(customer -> customer.getTransDtlsList()).findAny();

		if (!customerTransDtls.isPresent()) {
			log.error("no such customer in system");
			throw new CustomerNotFoundException("customer with Id " + customerId + " is not registered with us");

		}

		return customerTransDtls.get();
	}

	private Integer getUnrewardedTrnsAmountSpentInThreeMonthsDuration(List<TransactionDtls> customerTransDtls) {
		log.debug("finding transactions  elgibile to get rewarded in three months duration for this customer");

		List<TransactionDtls> rewardsEligibleTransList = customerTransDtls.stream()
				.filter(trans -> !trans.getIsTransactionAlreadyRewarded()).filter(trans -> (Duration
						.between(LocalDate.of(2022, 5, 15).atStartOfDay(), trans.getTransactionDate()).toDays() < 90))
				.collect(Collectors.toList());

		Double totalSpend = rewardsEligibleTransList.stream().map(trans -> trans.getSpentAmount()).reduce(0.00,
				(sum, elt) -> sum + elt);

		if (totalSpend == null || totalSpend == 0.00) {
			log.error("no rewards to be given out");
			throw new RewardsNotApplicableException("rewards are not applicable or already redeemed");
		}

		Double greaterThan50SpendRewards = totalSpend - Double.valueOf(50.0) > 1.00
				? totalSpend - Double.valueOf(50.0) * 1
				: 0;
		Double greaterThan100SpendRewards = totalSpend - Double.valueOf(100.0) > 1.00
				? totalSpend - Double.valueOf(50.0) * 1
				: 0;

		Integer reward = greaterThan50SpendRewards.intValue() + (Integer) greaterThan100SpendRewards.intValue();
		log.debug("reward earned by customer : {} ", reward);
		this.updatePaidRewardsStatus(rewardsEligibleTransList);
		return reward;
	}

	private void updatePaidRewardsStatus(List<TransactionDtls> rewardedTransList) {
		log.debug("updating the status of the rewarded ones ");
		rewardedTransList.stream().forEach(trans -> trans.setIsTransactionAlreadyRewarded(true));
	}

	private List<Customer> getMockData() {

		log.debug("preparing mock data during application boot up");
		/**
		 * one trans greater 50 and one greater than 100
		 */

		TransactionDtls t1c1 = new TransactionDtls("1", 54.0, CurrencyDetails.USD,
				LocalDate.of(2022, 4, 4).atStartOfDay(), false);
		TransactionDtls t2c1 = new TransactionDtls("2", 100.0, CurrencyDetails.USD,
				LocalDate.of(2022, 3, 4).atStartOfDay(), false);

		Customer c1 = new Customer(0, 0, "C1", Arrays.asList(t1c1, t2c1));

		/**
		 * Both trans are less than 50
		 */
		TransactionDtls t1c2 = new TransactionDtls("3", 10.0, CurrencyDetails.USD,
				LocalDate.of(2022, 4, 4).atStartOfDay(), false);
		TransactionDtls t2c2 = new TransactionDtls("4", 10.0, CurrencyDetails.USD,
				LocalDate.of(2022, 4, 4).atStartOfDay(), false);

		Customer c2 = new Customer(0, 0, "C2", Arrays.asList(t1c2, t2c2));

		/**
		 * Both trans greater than 100
		 */
		TransactionDtls t1c3 = new TransactionDtls("5", 1000.0, CurrencyDetails.USD,
				LocalDate.of(2022, 4, 4).atStartOfDay(), false);
		TransactionDtls t2c3 = new TransactionDtls("6", 1000.0, CurrencyDetails.USD,
				LocalDate.of(2022, 4, 4).atStartOfDay(), false);

		Customer c3 = new Customer(0, 0, "C3", Arrays.asList(t1c3, t2c3));

		/**
		 * few are already awarded and non awarded one is less than 50$
		 */
		TransactionDtls t1c4 = new TransactionDtls("8", 1000.0, CurrencyDetails.USD,
				LocalDate.of(2021, 4, 4).atStartOfDay(), true);
		TransactionDtls t2c4 = new TransactionDtls("9", 49.0, CurrencyDetails.USD,
				LocalDate.of(2021, 4, 4).atStartOfDay(), false);

		Customer c4 = new Customer(0, 0, "C4", Arrays.asList(t1c4, t2c4));

		return Arrays.asList(c1, c2, c3, c4);
	}

}
