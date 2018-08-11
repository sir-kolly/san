package egerton.hospital.service.lab;

import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.test.Test;

import java.util.Date;
import java.util.List;

public interface LabService {
    boolean submitTestToBeDone(Test test);
    boolean updateTestToBeDone(Test test);
    boolean checkIfTestIsAlreadySubmitted(Test test);
    boolean checkIfResultIsAlreadySubmitted(Lab lab);
    List<Test> getTestToBeDone(Test test);

    boolean submitLabResult(Lab lab);
    List<Lab>getLabReport(Patient patient);
    List<Lab> previousReports(Date date, Patient patient);
    List<Lab> todayLabReport(Date date, Patient patient);

    boolean updateTestAfterSubmitted(Test test);


}
