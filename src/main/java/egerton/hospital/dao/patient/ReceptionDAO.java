package egerton.hospital.dao.patient;

import egerton.hospital.model.patient.Patient;

public interface ReceptionDAO {
    boolean save(Patient patient);
    boolean update(Patient patient);
    Patient checkIfExisting(Patient patient);
    Patient patientInfo(Patient patient);
}
