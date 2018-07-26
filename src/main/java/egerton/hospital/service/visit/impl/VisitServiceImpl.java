package egerton.hospital.service.visit.impl;

import egerton.hospital.dao.visit.VisitDAO;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.visit.Visit;
import egerton.hospital.service.visit.VisitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class VisitServiceImpl  implements VisitService {
    @Inject
    private VisitDAO visitDAO;

    @Override
    public boolean saveVisit(Visit visit) {
        return this.getVisitDAO().saveVisit(visit);
    }


    @Override
    public List<Visit> patientVisits() {
        return this.getVisitDAO().patientVisits();
    }

    @Override
    public List<Visit> visitsToday(Date date) {
        return this.getVisitDAO().visitsToday(date);
    }

    @Override
    public boolean updateVisit(Visit visit) {
        return this.getVisitDAO().updateVisit(visit);
    }

    @Override
    public boolean checkIfVisitIsAlreadyRecordedForPatient(Visit visit) {
        return this.getVisitDAO().checkIfVisitIsAlreadyRecordedForPatient(visit);
    }

    public VisitDAO getVisitDAO() {
        return visitDAO;
    }

    public void setVisitDAO(VisitDAO visitDAO) {
        this.visitDAO = visitDAO;
    }
}
