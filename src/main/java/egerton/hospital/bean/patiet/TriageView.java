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
@Named("triageView")
public class TriageView {
    private Triage triage;
    private Patient patient;
    private Visit visit;
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
        context.getExternalContext().getFlash().setKeepMessages(true);
        try {
            triage=new Triage(generateRandomNumber(),getTriage().getWeight(),getTriage().getPressure(),new Date(),new Date(),getPatient());
            if(this.getTriageService().saveTriageRecord(triage)){
                Message.message("Record Submitted",FacesMessage.SEVERITY_INFO);
                context.getExternalContext().getFlash().setKeepMessages(true);
                triage=new Triage();
                return "triage-saved";
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_INFO);
        }
        return null;
    }
    public Set<String> visitorsToday(){
        try {
            numbers=new HashSet<>();
            visits=getVisitService().visitsToday(new Date());
            if(!visits.isEmpty()){
                for (int i=0;i<visits.size();i++){
                    numbers.add(visits.get(i).getPatient().getPatientNumber());
                }
                return numbers;
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_WARN);
        }
        return null;
    }
    public Patient patientInfo(){
        if(!visits.isEmpty()){
            for (int i=0;i<visits.size();i++){
                String number,num;
                number=visits.get(i).getPatient().getPatientNumber();
                num=patient.getPatientNumber();
                if(number.equalsIgnoreCase(num)){
                    patient=visits.get(i).getPatient();
                }
            }
            if(patient!=null){
                PrimeFaces.current().ajax().update("patientInfoForm");
                return patient;
            }
        }
        return null;
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

    public String newTriageRecordUrl(){
        return ("/nurse/triage.xhtml");
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

}
