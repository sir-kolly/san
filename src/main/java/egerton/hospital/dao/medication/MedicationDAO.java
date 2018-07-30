package egerton.hospital.dao.medication;

import egerton.hospital.model.illness.Illness;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.medication.Medication;

import java.util.List;

public interface MedicationDAO {
    boolean saveTreatmentRecord(Medication medication);
    List<Medication> getPatientMedications(Patient patient);

    boolean saveIllness(Illness illness);
    List<Illness> getPatientIllnesses(Patient patient);

    boolean checkIfIllnessIsSavedAlready(Illness illness);

    boolean checkIfMedicationIsSavedAlready(Medication medication);
    List<Illness> getTodayPatientIllnesses(Illness illness);

    Illness illnessInfo(Illness illness);
}
