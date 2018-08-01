package egerton.hospital.service.admission.impl;

import egerton.hospital.dao.admission.AdmissionDAO;
import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.service.admission.AdmissionService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;
@Transactional
@Repository
public class AdmissionServiceImpl implements AdmissionService {
    @Inject
    private AdmissionDAO admissionDAO;

    @Override
    public boolean admitPatient(Admission admission) {
        return this.getAdmissionDAO().admitPatient(admission);
    }

    @Override
    public boolean checkIfAdmittedAlready(Admission admission) {
        return this.getAdmissionDAO().checkIfAdmittedAlready(admission);
    }

    @Override
    public boolean update(Admission admission) {
        return this.admissionDAO.update(admission);
    }

    @Override
    public Set<Admission> patientPreviousAdmissionDetails(Admission admission) {
        return this.getAdmissionDAO().patientPreviousAdmissionDetails(admission);
    }

    @Override
    public Admission todayPatientAdmission(Admission admission) {
        return this.getAdmissionDAO().todayPatientAdmission(admission);
    }

    public AdmissionDAO getAdmissionDAO() {
        return admissionDAO;
    }

    public void setAdmissionDAO(AdmissionDAO admissionDAO) {
        this.admissionDAO = admissionDAO;
    }
}
