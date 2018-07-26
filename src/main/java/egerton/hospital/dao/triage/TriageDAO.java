package egerton.hospital.dao.triage;

import egerton.hospital.model.triage.Triage;

public interface TriageDAO {
    boolean saveTriageRecord(Triage triage);
    boolean checkIfTriageRecordIsAlreadySaved(Triage triage);
    Triage getTriageResult(Triage triage);
}
