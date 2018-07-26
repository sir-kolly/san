package egerton.hospital.service.patient;

import egerton.hospital.model.patient.Patient;

public interface PatientService {
    boolean save(Patient patient);
    Patient checkIfExisting(Patient patient);
    Patient patientInfo(Patient patient);



}
