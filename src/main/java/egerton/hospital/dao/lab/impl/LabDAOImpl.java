package egerton.hospital.dao.lab.impl;

import egerton.hospital.dao.lab.LabDAO;
import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.test.Test;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Repository
public class LabDAOImpl implements LabDAO {
    @Inject
    private SessionFactory sessionFactory;

    @Override
    public boolean submitTestToBeDone(Test test) {
        this.getSessionFactory().getCurrentSession().save(test);
        return true;
    }

    @Override
    public boolean updateTest(Test test) {
        this.getSessionFactory().getCurrentSession().update(test);
        return false;
    }

    @Override
    public boolean checkIfTestIsAlreadySubmitted(Test test) {
        test=this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfTestIsAlreadySubmitted",Test.class)
                .setParameter("test",test.getTest()).setParameter("date",test.getDate()).stream().findFirst().orElse(null);
        if(test.equals(null))
            return false;
        return true;
    }

    @Override
    public List<Test> getTestToBeDone(Test test) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("getTestToBeDone",Test.class)
                .setParameter("date",test.getDate()).getResultList());
    }

    @Override
    public boolean submitLabResult(Lab lab) {
        this.getSessionFactory().getCurrentSession().save(lab);
        return true;
    }

    @Override
    public List<Lab> getLabReport(Patient patient) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("getLastLabReport",Lab.class)
                .setParameter("number",patient.getPatientNumber()).setParameter("date",new Date()).getResultList());
    }

    @Override
    public List<Lab> todayLabReport(Date date, Patient patient) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("todayLabReport",Lab.class)
                .setParameter("date",date).setParameter("number",patient.getPatientNumber()).getResultList());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
