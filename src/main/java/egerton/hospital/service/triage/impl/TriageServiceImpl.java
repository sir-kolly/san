package egerton.hospital.service.triage.impl;

import egerton.hospital.dao.triage.TriageDAO;
import egerton.hospital.model.triage.Triage;
import egerton.hospital.service.triage.TriageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Service
public class TriageServiceImpl implements TriageService {
    @Inject
    private TriageDAO triageDAO;
    @Override
    public boolean saveTriageRecord(Triage triage) {
        return this.getTriageDAO().saveTriageRecord(triage);
    }

    @Override
    public boolean update(Triage triage) {
        return this.getTriageDAO().update(triage);
    }

    @Override
    public boolean checkIfTriageRecordIsAlreadySaved(Triage triage) {
        return this.getTriageDAO().checkIfTriageRecordIsAlreadySaved(triage);
    }

    @Override
    public Triage getTriageResult(Triage triage) {
        return this.getTriageDAO().getTriageResult(triage);
    }

    public TriageDAO getTriageDAO() {
        return triageDAO;
    }

    public void setTriageDAO(TriageDAO triageDAO) {
        this.triageDAO = triageDAO;
    }
}
