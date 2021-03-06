package egerton.hospital.dao.patient.impl;

import egerton.hospital.dao.patient.ReceptionDAO;
import egerton.hospital.model.patient.Patient;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class ReceptionDAOImpl implements ReceptionDAO {
    @Inject
    private SessionFactory sessionFactory;
    @Override
    public boolean save(Patient patient) {
        this.getSessionFactory().getCurrentSession().save(patient);
        return true;
    }

    @Override
    public boolean update(Patient patient) {
        this.getSessionFactory().getCurrentSession().update(patient);
        return true;
    }

    @Override
    public Patient checkIfExisting(Patient patient) {
        return this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfExisting",Patient.class)
                .setParameter("firstname",patient.getFirstName())
                .setParameter("lastname",patient.getLastName())
                .setParameter("phone",patient.getPhone())
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Patient patientInfo(Patient patient) {
        return this.getSessionFactory().getCurrentSession().find(Patient.class,patient.getPatientNumber());
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
