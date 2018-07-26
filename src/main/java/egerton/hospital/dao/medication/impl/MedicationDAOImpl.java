package egerton.hospital.dao.medication.impl;

import egerton.hospital.dao.medication.MedicationDAO;
import egerton.hospital.model.illness.Illness;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.medication.Medication;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MedicationDAOImpl implements MedicationDAO {
    @Inject
    private SessionFactory sessionFactory;

    @Override
    public boolean saveTreatmentRecord(Medication medication) {
        this.getSessionFactory().getCurrentSession().save(medication);
        return true;
    }

    @Override
    public List<Medication> getPatientMedications(Patient patient) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("patientMedications",Medication.class)
                .setParameter("number",patient.getPatientNumber()).getResultList());
    }

    @Override
    public boolean saveIllness(Illness illness) {
        this.getSessionFactory().getCurrentSession().save(illness);
        return true;
    }

    @Override
    public List<Illness> getPatientIllnesses(Patient patient) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("patientIllnesses",Illness.class)
                .setParameter("number",patient.getPatientNumber()).getResultList());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
