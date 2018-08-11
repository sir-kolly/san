package egerton.hospital.bean.patiet;

import egerton.hospital.message.Message;
import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.medication.Medicate;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.test.Test;
import egerton.hospital.model.visit.Visit;
import egerton.hospital.service.lab.LabService;
import egerton.hospital.service.medication.MedicationService;
import egerton.hospital.service.visit.VisitService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@ViewScoped
@Named
public class ChemistView {
    private Medicate medicate;
    private Patient patient;
    private Visit visit;

    private boolean testSelected,testsAvailable;
    @Inject
    private MedicationService medicationService;
    @Inject
    private VisitService visitService;

    private List<Medicate> medications;
    private List<Visit> visits;

    private boolean patientRecordAvailable;

    public ChemistView(){
        medicate=new Medicate();
        patient=new Patient();
        visit=new Visit();
    }

    public void patientMedications(){
        try {
            visits=this.getVisitService().visitsToday(new Date());
            if (!visits.isEmpty()){
                medications=this.getMedicationService().getPatientMedications(visits.get(0).getPatient());
            }
        }catch (Exception e){}
    }

    public Medicate getMedicate() {
        return medicate;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public VisitService getVisitService() {
        return visitService;
    }

    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public void setMedicate(Medicate medicate) {
        this.medicate = medicate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isTestSelected() {
        return testSelected;
    }

    public void setTestSelected(boolean testSelected) {
        this.testSelected = testSelected;
    }

    public boolean isTestsAvailable() {
        return testsAvailable;
    }

    public void setTestsAvailable(boolean testsAvailable) {
        this.testsAvailable = testsAvailable;
    }

    public MedicationService getMedicationService() {
        return medicationService;
    }

    public void setMedicationService(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    public List<Medicate> getMedications() {
        return medications;
    }

    public void setMedications(List<Medicate> medications) {
        this.medications = medications;
    }

    public boolean isPatientRecordAvailable() {
        return patientRecordAvailable;
    }

    public void setPatientRecordAvailable(boolean patientRecordAvailable) {
        this.patientRecordAvailable = patientRecordAvailable;
    }
}
