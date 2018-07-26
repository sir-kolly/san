package egerton.hospital.service.medication;

import egerton.hospital.model.illness.Illness;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.medication.Medication;

import java.util.List;

public interface MedicationService {
    boolean saveTreatmentRecord(Medication medication);
    List<Medication> getPatientMedications(Patient patient);

    boolean saveIllness(Illness illness);
    List<Illness> getPatientIllnesses(Patient patient);
}
