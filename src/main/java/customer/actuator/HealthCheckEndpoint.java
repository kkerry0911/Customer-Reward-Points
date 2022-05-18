package customer.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * actuator class with readiness probe
 */

@Component
@Endpoint(id = "ready")
public class HealthCheckEndpoint implements HealthIndicator {

	private String ready = "NOT_READY";

	@ReadOperation
	public String getReadinessStatus() {
		return ready;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void setReady() {
		ready = "READY";
	}

	@Override
	public Health health() {
		 Status status = ready.equalsIgnoreCase("READY") ? Status.UP : Status.DOWN;  
		 return Health.status(status).build();
	}
}
