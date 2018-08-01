package egerton.hospital.service.medication.impl;

import egerton.hospital.dao.medication.MedicationDAO;
import egerton.hospital.model.illness.Disease;
import egerton.hospital.model.medication.Medicate;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.service.medication.MedicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
@Transactional
@Service
public class MedicationServiceImpl implements MedicationService {
    @Inject
    private MedicationDAO medicationDAO;
    @Override
    public boolean saveTreatmentRecord(Medicate medicate) {
        return this.getMedicationDAO().saveTreatmentRecord(medicate);
    }

    @Override
    public List<Medicate> getPatientMedications(Patient patient) {
        return this.getMedicationDAO().getPatientMedications(patient);
    }

    @Override
    public boolean saveIllness(Disease disease) {
        return this.getMedicationDAO().saveIllness(disease);
    }

    @Override
    public List<Disease> getPatientIllnesses(Patient patient) {
        return this.getMedicationDAO().getPatientIllnesses(patient);
    }

    @Override
    public Disease patientIllness(Disease disease) {
        return this.getMedicationDAO().patientIllness(disease);
    }

    @Override
    public boolean checkIfIllnessIsSavedAlready(Disease disease) {
        return this.getMedicationDAO().checkIfIllnessIsSavedAlready(disease);
    }


    @Override
    public boolean checkIfMedicationIsSavedAlready(Medicate medicate) {
        return this.getMedicationDAO().checkIfMedicationIsSavedAlready(medicate);
    }

    @Override
    public List<Disease> getTodayPatientIllnesses(Disease disease) {
        return this.getMedicationDAO().getTodayPatientIllnesses(disease);
    }

    @Override
    public Disease illnessInfo(Disease disease) {
        return this.getMedicationDAO().illnessInfo(disease);
    }

    public MedicationDAO getMedicationDAO() {
        return medicationDAO;
    }

    public void setMedicationDAO(MedicationDAO medicationDAO) {
        this.medicationDAO = medicationDAO;
    }
}
