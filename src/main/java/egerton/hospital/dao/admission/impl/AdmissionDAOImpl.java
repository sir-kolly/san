package egerton.hospital.dao.admission.impl;

import egerton.hospital.dao.admission.AdmissionDAO;
import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.patient.Patient;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;
@Repository
public class AdmissionDAOImpl implements AdmissionDAO {
    @Inject
    private SessionFactory sessionFactory;

    @Override
    public boolean admitPatient(Admission admission) {
        this.getSessionFactory().getCurrentSession().save(admission);
        return true;
    }

    @Override
    public Set<Admission> admissionDetails(Patient patient) {
        return new LinkedHashSet<>(this.getSessionFactory().getCurrentSession().createNamedQuery("patientAdmissionDetails",Admission.class)
                .setParameter("number",patient.getPatientNumber()).getResultList());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
