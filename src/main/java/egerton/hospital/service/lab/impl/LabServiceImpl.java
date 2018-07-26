package egerton.hospital.service.lab.impl;

import egerton.hospital.dao.lab.LabDAO;
import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.test.Test;
import egerton.hospital.service.lab.LabService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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

    public LabDAO getLabDAO() {
        return labDAO;
    }

    public void setLabDAO(LabDAO labDAO) {
        this.labDAO = labDAO;
    }
}
