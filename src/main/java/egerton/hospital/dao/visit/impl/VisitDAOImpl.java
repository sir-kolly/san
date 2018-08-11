package egerton.hospital.dao.visit.impl;

import egerton.hospital.dao.visit.VisitDAO;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.visit.Visit;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
@Repository
public class VisitDAOImpl implements VisitDAO {
    @Inject
    private SessionFactory sessionFactory;

    @Override
    public boolean saveVisit(Visit visit) {
        this.getSessionFactory().getCurrentSession().save(visit);
        return true;
    }



    @Override
    public List<Visit> patientVisits(Patient patient) {
        return new LinkedList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("patientVisits",Visit.class)
                .setParameter("number",patient.getPatientNumber())
                .getResultList());
    }

    @Override
    public Visit visitorToday(Date date) {
        return this.getSessionFactory().getCurrentSession().createNamedQuery("visitsToday",Visit.class)
                .setParameter("d",date).setMaxResults(1).stream().findFirst().orElse(null);
    }

    @Override
    public List<Visit> visitsForTheTriageToday(Date date) {
        return this.getSessionFactory().getCurrentSession().createNamedQuery("visitsForTheTriageToday",Visit.class)
                .setParameter("d",date).setMaxResults(1).getResultList();
    }

    @Override
    public boolean checkIfVisitIsAlreadyRecordedForPatient(Visit visit) {
        visit=this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfVisitIsAlreadyRecordedForPatient",Visit.class)
                .setParameter("date",visit.getDate())
                .setParameter("number",visit.getPatient().getPatientNumber())
                .stream().findFirst().orElse(null);
        if(visit==null){
            return false;
        }
        return true;
    }

    @Override
    public List<Visit> visitsToday(Date date) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("visitorsToday",Visit.class)
                .setParameter("date",date).getResultList());
    }

    @Override
    public boolean updateVisit(Visit visit) {
        this.getSessionFactory().getCurrentSession().update(visit);
        return true;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
