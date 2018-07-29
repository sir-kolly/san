package egerton.hospital.dao.admission.impl;

import egerton.hospital.dao.admission.AdmissionDAO;
import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.medication.Medication;
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
    public Set<Admission> admissionDetails(Patient patient) {
        return new LinkedHashSet<>(this.getSessionFactory().getCurrentSession().createNamedQuery("patientAdmissionDetails",Admission.class)
                .setParameter("number",patient.getPatientNumber()).getResultList());
    }

    @Override
    public boolean checkIfAdmittedAlready(Admission admission) {
        admission=this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfAdmittedAlready",Admission.class)
                .setParameter("number",admission.getPatient().getPatientNumber())
                .setParameter("date",new Date(),TemporalType.DATE).stream().findFirst().orElse(null);

        return admission==null ? false:true;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
