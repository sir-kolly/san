package egerton.hospital.service.admission;

import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.patient.Patient;

import java.util.Set;

public interface AdmissionService {
    boolean admitPatient(Admission admission);
    Set<Admission> admissionDetails(Patient patient);

    boolean checkIfAdmittedAlready(Admission admission);
}
