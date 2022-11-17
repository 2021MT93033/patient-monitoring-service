/**
 * 
 */
package com.bits.assignment.universalt10.patient.patientmontitoringservice.service.impl;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bits.assignment.universalt10.patient.patientmontitoringservice.model.PatientData;
import com.bits.assignment.universalt10.patient.patientmontitoringservice.model.SimpleMessage;
import com.bits.assignment.universalt10.patient.patientmontitoringservice.service.PatientMonitoringService;

/**
 * @author Vicky
 *
 */
@Service
public class PatientMonitoringServiceImpl implements PatientMonitoringService {

	@Value("${gateway.url}")
	private String gatewayURL;

	@Autowired
	private RestTemplate restTemplate;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SimpleMessage monitorPatientData(PatientData patientData) {
		// TODO Auto-generated method stub

		if (!emptyCheck(patientData.getBloodPressure())) {
			Integer upperbound = Integer.valueOf(patientData.getBloodPressure().split("/")[0]);
			Integer lowerbound = Integer.valueOf(patientData.getBloodPressure().split("/")[0]);
			if (((upperbound > 120) && (upperbound < 140)) || ((lowerbound > 80) && lowerbound < 90)) {
				patientData.setBloodPressureStatus("The BloodPressure is slightly high");
			}

			if ((upperbound > 140) || (lowerbound > 90)) {
				patientData.setBloodPressureStatus("The BloodPressure is high");
			}
		}

		if (!emptyCheck(patientData.getBmi())) {
			float bmi = Float.valueOf(patientData.getBmi());
			if (bmi < 18.5) {
				patientData.setBmiStatus("BMI is not normal");
			}
			if (bmi > 18.5 && bmi < 24.9) {
				patientData.setBmiStatus("BMI is normal");
			}
			if (bmi > 24.9) {
				patientData.setBmiStatus("BMI is obese");
			}
		}

		if (!emptyCheck(patientData.getOxygenLevel())) {
			Integer oxy = Integer.valueOf(patientData.getOxygenLevel());
			if (oxy >= 95 && oxy <= 100) {
				patientData.setOxygenLevelStatus("Oxygen Level is normal");
			} else {
				patientData.setOxygenLevelStatus("Oxygen Level is not normal");
			}
		}

		if (!emptyCheck(patientData.getSleepTracking())) {
			Integer oxy = Integer.valueOf(patientData.getSleepTracking());
			if (oxy >= 9 && oxy <= 11) {
				patientData.setSleepTrackingStatus("Sleep is normal");
			} else {
				patientData.setSleepTrackingStatus("Sleep is not normal");
			}
		}

		if (!emptyCheck(patientData.getStepCount())) {

			Integer oxy = Integer.valueOf(patientData.getStepCount());
			if (oxy >= 2000 && oxy <= 3000) {
				patientData.setStepCountStatus("stepcount is normal");
			} else {
				patientData.setStepCountStatus("stepcount is not normal");
			}

		}

		if (!emptyCheck(patientData.getTemperature())) {

			float temp = Float.valueOf(patientData.getTemperature());

			if (temp >= 96 && temp <= 99.9) {
				patientData.setTemperatureStatus("The temperature is normal");
			}

			if (temp > 99.9) {
				patientData.setTemperatureStatus("The temperature is high");
			}

			if (temp < 96) {
				patientData.setTemperatureStatus("The temperature is low");
			}

		}

		SimpleMessage message = sendPatientDataToDoctor(patientData);

		return message;
	}

	private SimpleMessage sendPatientDataToDoctor(PatientData patientData) {
		String endpoint = gatewayURL + "/doctor-alert-service/patientRecord";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<PatientData> request = new HttpEntity<>(patientData, headers);

		ResponseEntity<SimpleMessage> message = restTemplate.postForEntity(endpoint, request, SimpleMessage.class);

		return message.getBody();
	}

	private boolean emptyCheck(String value) {
		boolean emptyCheck = false;
		if (StringUtils.isAllBlank(value) && StringUtils.isAllEmpty(value)) {
			emptyCheck = true;
			return emptyCheck;
		}
		return emptyCheck;
	}

}
