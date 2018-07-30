package egerton.hospital.service.reception;

import egerton.hospital.model.patient.Patient;

public interface ReceptionService {
    boolean save(Patient patient);
    boolean update(Patient patient);
    Patient checkIfExisting(Patient patient);
    Patient patientInfo(Patient patient);
}
