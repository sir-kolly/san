package egerton.hospital.bean.patiet;

import egerton.hospital.message.Message;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.triage.Triage;
import egerton.hospital.model.visit.Visit;
import egerton.hospital.service.triage.TriageService;
import egerton.hospital.service.visit.VisitService;
import egerton.hospital.utill.Utill;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@ViewScoped
@Named
public class TriageView {
    private Triage triage;
    private Patient patient;
    private Visit visit;
    private boolean patientRecordAvailable;
    @Inject
    private TriageService triageService;
    @Inject
    private VisitService visitService;

    private Set<String>numbers;
    private List<Visit>visits;

    public TriageView(){
        triage =new Triage();
        patient=new Patient();
        visit=new Visit();
    }

    public String saveTriageFindings(){
        FacesContext context=FacesContext.getCurrentInstance();
        try {
            triage=new Triage(generateRandomNumber(),getTriage().getWeight(),getTriage().getPressure(),new Date(),new Date(),getPatient());
            if(!this.getTriageService().checkIfTriageRecordIsAlreadySaved(triage))
                if(this.getTriageService().saveTriageRecord(triage)){
                    Message.message("Record Submitted",FacesMessage.SEVERITY_INFO);
                    context.getExternalContext().getFlash().setKeepMessages(true);
                return ("/faces/nurse/record-measurement.xhtml?faces-redirect=true");
            }
            else return ("/faces/nurse/record-measurement.xhtml?faces-redirect=true");
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_INFO);
        }
        return null;
    }
    public String updateTriageRecord(){
        if(this.getTriageService().update(triage))
            return  ("/faces/nurse/record-measurement.xhtml?faces-redirect=true");
        return null;
    }

    public Patient visitorsToday(){
        try {
            visits=this.getVisitService().visiting(new Date());
            if(!visits.isEmpty()){
                System.out.println(visits.size());
                patient=visits.get(0).getPatient();
                if(patient!=null){
                    this.setPatientRecordAvailable(true);
                    return patient;
                }
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_WARN);
        }
        return null;
    }
    public String refresh(){
        return  ("/faces/nurse/record-measurement.xhtml?faces-redirect=true");
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

    public Set<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<String> numbers) {
        this.numbers = numbers;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    private String generateRandomNumber(){
        return UUID.randomUUID().toString().replace("-","")
                .substring(1,16).toUpperCase();
    }
    public Triage getTriage() {
        return triage;
    }

    public Patient getPatient() {
        return patient;
    }

    public TriageService getTriageService() {
        return triageService;
    }

    public void setTriageService(TriageService triageService) {
        this.triageService = triageService;
    }

    public void setTriage(Triage triage) {
        this.triage = triage;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isPatientRecordAvailable() {
        return patientRecordAvailable;
    }

    public void setPatientRecordAvailable(boolean patientRecordAvailable) {
        this.patientRecordAvailable = patientRecordAvailable;
    }
}
