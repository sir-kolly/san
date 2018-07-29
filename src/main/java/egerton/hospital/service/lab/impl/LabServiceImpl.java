package egerton.hospital.service.lab.impl;

import egerton.hospital.dao.lab.LabDAO;
import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.test.Test;
import egerton.hospital.service.lab.LabService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class LabServiceImpl implements LabService {
    @Inject
    private LabDAO labDAO;
    @Override
    public boolean submitTestToBeDone(Test test) {
        return this.getLabDAO().submitTestToBeDone(test);
    }

    @Override
    public boolean updateTestToBeDone(Test test) {
        return this.getLabDAO().updateTest(test);
    }

    @Override
    public boolean checkIfTestIsAlreadySubmitted(Test test) {
        return this.getLabDAO().checkIfTestIsAlreadySubmitted(test);
    }

    @Override
    public boolean checkIfResultIsAlreadySubmitted(Lab lab) {
        return this.getLabDAO().checkIfResultIsAlreadySubmitted(lab);
    }

    @Override
    public List<Test> getTestToBeDone(Test test) {
        return this.getLabDAO().getTestToBeDone(test);
    }

    @Override
    public boolean submitLabResult(Lab lab) {
        return this.getLabDAO().submitLabResult(lab);
    }

    @Override
    public List<Lab> getLabReport(Patient patient) {
        return this.getLabDAO().getLabReport(patient);
    }

    @Override
    public List<Lab> previousReports(Date date, Patient patient) {
        return this.getLabDAO().previousReports(date, patient);
    }

    @Override
    public List<Lab> todayLabReport(Date date, Patient patient) {
        return this.getLabDAO().todayLabReport(date,patient);
    }

    @Override
    public boolean updateTestAfterSubmitted(Test test) {
        return this.getLabDAO().updateTestAfterSubmitted(test);
    }

    public LabDAO getLabDAO() {
        return labDAO;
    }

    public void setLabDAO(LabDAO labDAO) {
        this.labDAO = labDAO;
    }
}
