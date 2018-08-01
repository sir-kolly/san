package egerton.hospital.dao.medication.impl;

import egerton.hospital.dao.medication.MedicationDAO;
import egerton.hospital.model.illness.Disease;
import egerton.hospital.model.medication.Medicate;
import egerton.hospital.model.patient.Patient;
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
    public boolean saveTreatmentRecord(Medicate medicate) {
        this.getSessionFactory().getCurrentSession().save(medicate);
        return true;
    }

    @Override
    public List<Medicate> getPatientMedications(Patient patient) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("patientMedications",Medicate.class)
                .setParameter("number",patient.getPatientNumber()).getResultList());
    }

    @Override
    public boolean saveIllness(Disease disease) {
        this.getSessionFactory().getCurrentSession().save(disease);
        return true;
    }

    @Override
    public List<Disease> getPatientIllnesses(Patient patient) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("patientIllnesses",Disease.class)
                .setParameter("number",patient.getPatientNumber()).getResultList());
    }

    @Override
    public Disease patientIllness(Disease disease) {
        return this.getSessionFactory().getCurrentSession().createNamedQuery("patientIllnessInfo",Disease.class)
                .setParameter("ill",disease.getIllness()).setParameter("number",disease.getPatient().getPatientNumber())
                .setParameter("date",disease.getDate()).stream().findFirst().orElse(null);
    }

    @Override
    public boolean checkIfIllnessIsSavedAlready(Disease  disease) {
         disease=this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfIllnessIsSavedAlready",Disease.class)
                .setParameter("ill",disease.getIllness()).setParameter("number",disease.getPatient().getPatientNumber())
                .setParameter("date",disease.getDate()).stream().findFirst().orElse(null);

        return disease==null ? false:true;
    }

    @Override
    public boolean checkIfMedicationIsSavedAlready(Medicate medicate) {
        medicate=this.getSessionFactory().getCurrentSession().createNamedQuery("checkIfMedicationIsSavedAlready",Medicate.class)
                .setParameter("name",medicate.getMedName()).setParameter("number",medicate.getPatient().getPatientNumber())
                .setParameter("illno",medicate.getDisease().getIllnessNumber())
                .stream().findFirst().orElse(null);

        return medicate==null ? false:true;
    }

    @Override
    public List<Disease> getTodayPatientIllnesses(Disease disease) {
        return new ArrayList<>(this.getSessionFactory().getCurrentSession().createNamedQuery("getTodayPatientIllnesses",Disease.class)
                .setParameter("number",disease.getPatient().getPatientNumber()).setParameter("date",disease.getDate()).getResultList());
    }

    @Override
    public Disease illnessInfo(Disease disease) {
        return this.getSessionFactory().getCurrentSession().find(Disease.class,disease.getIllnessNumber());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
