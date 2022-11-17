/**
 * 
 */
package com.bits.assignment.universalt10.patient.patientmontitoringservice.service;

import com.bits.assignment.universalt10.patient.patientmontitoringservice.model.PatientData;
import com.bits.assignment.universalt10.patient.patientmontitoringservice.model.SimpleMessage;

/**
 * @author Vicky
 *
 */
public interface PatientMonitoringService {

	SimpleMessage monitorPatientData(PatientData patientData);

}
