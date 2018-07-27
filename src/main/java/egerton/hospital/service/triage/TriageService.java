package egerton.hospital.service.triage;

import egerton.hospital.model.triage.Triage;

public interface TriageService {
    boolean saveTriageRecord(Triage triage);
    boolean update(Triage triage);
    boolean checkIfTriageRecordIsAlreadySaved(Triage triage);
    Triage getTriageResult(Triage triage);
}
