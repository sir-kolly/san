package egerton.hospital.service.patient.impl;

import egerton.hospital.dao.patient.PatientDAO;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.service.patient.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Service
public class PatientServiceImpl implements PatientService {
    @Inject
    private PatientDAO patientRecord;

    @Override
    public boolean save(Patient patient) {
        return this.patientRecord.save(patient);
    }

    @Override
    public Patient checkIfExisting(Patient patient) {
        return this.patientRecord.checkIfExisting(patient);
    }

    @Override
    public Patient patientInfo(Patient patient) {
        return this.patientRecord.patientInfo(patient);
    }

    public PatientDAO getPatientRecord() {
        return patientRecord;
    }

    public void setPatientRecord(PatientDAO patientRecord) {
        this.patientRecord = patientRecord;
    }
}
