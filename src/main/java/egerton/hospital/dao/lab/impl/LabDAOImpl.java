package egerton.hospital.dao.lab.impl;

import egerton.hospital.dao.lab.LabDAO;
import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.test.Test;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
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
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("getLabReport",Lab.class)
                .setParameter("number",patient.getPatientNumber()).getResultList());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
