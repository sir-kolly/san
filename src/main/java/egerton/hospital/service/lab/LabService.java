package egerton.hospital.service.lab;

import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.test.Test;

import java.util.List;

public interface LabService {
    boolean submitTestToBeDone(Test test);
    List<Test> getTestToBeDone(Test test);

    boolean submitLabResult(Lab lab);
    List<Lab>getLabReport(Patient patient);
}
