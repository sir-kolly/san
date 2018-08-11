package egerton.hospital.service.medication;

import egerton.hospital.model.illness.Disease;
import egerton.hospital.model.medication.Medicate;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.symptom.Symptom;

import java.util.Date;
import java.util.List;

public interface MedicationService {
    boolean saveTreatmentRecord(Medicate medicate);
    List<Medicate> getPatientMedications(Patient patient);

    boolean saveIllness(Disease disease);
    List<Disease> getPatientIllnesses(Patient patient);
    Disease patientIllness(Disease disease);
    boolean checkIfIllnessIsSavedAlready(Disease disease);

    boolean checkIfMedicationIsSavedAlready(Medicate medicate);
    List<Disease> getTodayPatientIllnesses(Disease disease);
    Disease illnessInfo(Disease disease);

    boolean saveSymptoms(Symptom symptom);
    Symptom getTodaySymptom(Date date, Patient patient);
}
