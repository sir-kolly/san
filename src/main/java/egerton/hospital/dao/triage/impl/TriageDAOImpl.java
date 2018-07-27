package egerton.hospital.dao.triage.impl;

import egerton.hospital.dao.triage.TriageDAO;
import egerton.hospital.model.triage.Triage;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
@Repository
public class TriageDAOImpl implements TriageDAO {
    @Inject
    private SessionFactory sessionFactory;

    @Override
    public boolean saveTriageRecord(Triage triage) {
        this.getSessionFactory().getCurrentSession().save(triage);
        return true;
    }

    @Override
    public boolean update(Triage triage) {
        this.getSessionFactory().getCurrentSession().update(triage);
        return true;
    }

    @Override
    public boolean checkIfTriageRecordIsAlreadySaved(Triage triage) {
        triage=this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfTriageRecordIsAlreadySaved",Triage.class)
                .setParameter("date",triage.getDate())
                .setParameter("number",triage.getPatient().getPatientNumber())
                .stream().findFirst().orElse(null);
        if(triage==null){
            return false;
        }
        return true;
    }

    @Override
    public Triage getTriageResult(Triage triage) {
        return triage=this.getSessionFactory().getCurrentSession().createNamedQuery("getTriageResult",Triage.class)
                .setParameter("date",triage.getDate())
                .setParameter("number",triage.getPatient().getPatientNumber())
                .stream().findFirst().orElse(null);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
