package egerton.hospital.dao.admission.impl;

import egerton.hospital.dao.admission.AdmissionDAO;
import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.patient.Patient;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.TemporalType;
import java.util.Date;
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
    public Set<Admission> patientPreviousAdmissionDetails(Admission admission) {
        return new LinkedHashSet<>(this.getSessionFactory().getCurrentSession().createNamedQuery("patientPreviousAdmissionDetails",Admission.class)
                .setParameter("number",admission.getPatient().getPatientNumber()).setParameter("date",admission.getPatient().getPatientNumber())
                .getResultList());
    }


    @Override
    public boolean checkIfAdmittedAlready(Admission admission) {
        admission=this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfAdmittedAlready",Admission.class)
                .setParameter("number",admission.getPatient().getPatientNumber())
                .setParameter("date",admission.getDate()).stream().findFirst().orElse(null);

        return admission==null ? false:true;
    }

    @Override
    public boolean update(Admission admission) {
         this.getSessionFactory().getCurrentSession().update(admission);
         return true;
    }

    @Override
    public Admission todayPatientAdmission(Admission admission) {
        return this.getSessionFactory().getCurrentSession().createNamedQuery("todayPatientAdmission",Admission.class)
                .setParameter("number",admission.getPatient().getPatientNumber())
                .setParameter("date",admission.getDate()).stream().findFirst().orElse(null);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
