package egerton.hospital.dao.medication.impl;

import egerton.hospital.dao.medication.MedicationDAO;
import egerton.hospital.model.illness.Illness;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.medication.Medication;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public boolean checkIfIllnessIsSavedAlready(Illness illness) {
        illness=this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfIllnessIsSavedAlready",Illness.class)
                .setParameter("illness",illness.getIllness()).setParameter("number",illness.getPatient().getPatientNumber())
                .setParameter("date",illness.getDate()).stream().findFirst().orElse(null);

        return illness==null ? false:true;
    }

    @Override
    public boolean checkIfMedicationIsSavedAlready(Medication medication) {
        medication=this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfMedicationIsSavedAlready",Medication.class)
                .setParameter("product",medication.getProductName()).setParameter("number",medication.getPatient().getPatientNumber())
                .setParameter("date",new Date(),TemporalType.DATE).setParameter("illno",medication.getIllness().getIllnessNumber())
                .stream().findFirst().orElse(null);

        return medication==null ? false:true;
    }

    @Override
    public List<Illness> getTodayPatientIllnesses(Illness illness) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("getTodayPatientIllnesses",Illness.class)
                .setParameter("number",illness.getPatient().getPatientNumber()).setParameter("date",illness.getDate()).getResultList());
    }

    @Override
    public Illness illnessInfo(Illness illness) {
        return this.getSessionFactory().getCurrentSession().find(Illness.class,illness.getIllnessNumber());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
