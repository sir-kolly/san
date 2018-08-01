package egerton.hospital.dao.admission;

import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.patient.Patient;

import java.util.Set;

public interface AdmissionDAO {
    boolean admitPatient(Admission admission);
    boolean checkIfAdmittedAlready(Admission admission);
    boolean update(Admission admission);

    Set<Admission> patientPreviousAdmissionDetails(Admission admission);
    Admission todayPatientAdmission(Admission admission);
}
