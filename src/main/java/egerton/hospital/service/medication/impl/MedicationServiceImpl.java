package egerton.hospital.service.medication.impl;

import egerton.hospital.dao.medication.MedicationDAO;
import egerton.hospital.model.illness.Illness;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.medication.Medication;
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
    public boolean saveTreatmentRecord(Medication medication) {
        return this.getMedicationDAO().saveTreatmentRecord(medication);
    }

    @Override
    public List<Medication> getPatientMedications(Patient patient) {
        return this.getMedicationDAO().getPatientMedications(patient);
    }

    @Override
    public boolean saveIllness(Illness illness) {
        return this.getMedicationDAO().saveIllness(illness);
    }

    @Override
    public List<Illness> getPatientIllnesses(Patient patient) {
        return this.getMedicationDAO().getPatientIllnesses(patient);
    }

    public MedicationDAO getMedicationDAO() {
        return medicationDAO;
    }

    public void setMedicationDAO(MedicationDAO medicationDAO) {
        this.medicationDAO = medicationDAO;
    }
}
