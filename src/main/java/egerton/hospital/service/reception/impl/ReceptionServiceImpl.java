package egerton.hospital.service.reception.impl;

import egerton.hospital.dao.patient.ReceptionDAO;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.service.reception.ReceptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Service
public class ReceptionServiceImpl implements ReceptionService {
    @Inject
    private ReceptionDAO patientRecord;

    @Override
    public boolean save(Patient patient) {
        return this.patientRecord.save(patient);
    }

    @Override
    public boolean update(Patient patient) {
        return this.patientRecord.update(patient);
    }

    @Override
    public Patient checkIfExisting(Patient patient) {
        return this.patientRecord.checkIfExisting(patient);
    }

    @Override
    public Patient patientInfo(Patient patient) {
        return this.patientRecord.patientInfo(patient);
    }

    public ReceptionDAO getPatientRecord() {
        return patientRecord;
    }

    public void setPatientRecord(ReceptionDAO patientRecord) {
        this.patientRecord = patientRecord;
    }
}
