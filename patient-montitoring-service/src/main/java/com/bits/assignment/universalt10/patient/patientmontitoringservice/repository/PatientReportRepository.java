/**
 * 
 */
package com.bits.assignment.universalt10.patient.patientmontitoringservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bits.assignment.universalt10.patient.patientmontitoringservice.model.PatientReport;

/**
 * @author Vicky
 *
 */
@Repository
public interface PatientReportRepository extends MongoRepository<PatientReport, String> {

}
