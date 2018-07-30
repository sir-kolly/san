package egerton.hospital.dao.visit;

import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.visit.Visit;

import java.util.Date;
import java.util.List;

public interface VisitDAO {
    boolean saveVisit(Visit visit);
    List<Visit> patientVisits(Patient patient);
    List<Visit> visitsToday(Date date);
    List<Visit>visitsForTheTriageToday(Date date);
    boolean updateVisit(Visit visit);
    boolean checkIfVisitIsAlreadyRecordedForPatient(Visit visit);
}
