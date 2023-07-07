package adt.demo.rewardprogram.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4561076768642273105L;
	private String traceId;
    private Long customerId;
}
