/**
 * 
 */
package com.bits.assignment.universalt10.patient.patientmontitoringservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vicky
 *
 */
@Configuration
public class PatientMonitoringConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		return builder.build();
	}

}
