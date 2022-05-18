package customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import customer.response.ResponseTemplate;
import customer.service.RewardsCalculatorSvc;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RewardsCalculationController {

	@Autowired
	private RewardsCalculatorSvc rewardsCalculator;

	@GetMapping(path = "/api/v1/customer/{customerId}/rewards")
	public ResponseEntity<ResponseTemplate> calculcateRewards(@PathVariable String customerId) {
		log.info("calculating rewards");
		Integer rewards = rewardsCalculator.getRewardsEarned(customerId);
		ResponseTemplate template = new ResponseTemplate(customerId, rewards, rewards/3);
		return new ResponseEntity<ResponseTemplate>(template, HttpStatus.OK);
	}
	

}
