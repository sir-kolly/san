package egerton.hospital.service.visit;

import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.visit.Visit;

import java.util.Date;
import java.util.List;

public interface VisitService {
    boolean saveVisit(Visit visit);
    List<Visit> patientVisits(Patient patient);
    Visit visitorToday(Date date);
    List<Visit>visitsForTheTriageToday(Date date);
    boolean updateVisit(Visit visit);
    boolean checkIfVisitIsAlreadyRecordedForPatient(Visit visit);

}
