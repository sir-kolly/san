package egerton.hospital.bean.patiet;

import egerton.hospital.message.Message;
import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.test.Test;
import egerton.hospital.model.visit.Visit;
import egerton.hospital.service.lab.LabService;
import egerton.hospital.service.visit.VisitService;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@ViewScoped
@Named("labView")
public class LabView {
    private Lab lab;
    private Patient patient;
    private Test test;
    private Visit visit;

    private boolean testSelected,testsAvailable;
    @Inject
    private LabService labService;
    @Inject
    private VisitService visitService;

    private List<Test> tests,availableTests;
    private List<Lab> labResults;
    private List<Visit>visits,labQueue;
    private Set<String>numbers;

    private boolean patientRecordAvailable;

    public LabView(){
        lab=new Lab();
        test=new Test();
        patient=new Patient();
        visit=new Visit();
    }

    public String saveLabResult(){
        FacesContext context=FacesContext.getCurrentInstance();
        try {
            lab=new Lab(lab.getTestNumber(),lab.getTest(),lab.getResult(),lab.getComment(),new Date(),getPatient());
            if (this.getLabService().checkIfResultIsAlreadySubmitted(lab)){
                if(this.getLabService().submitLabResult(lab)){
                    test=new Test(lab.getTestNumber(),lab.getTest(),patient,true,new Date());
                    if(this.getLabService().updateTestAfterSubmitted(test)){
                        visit.setInQueue(true);
                        if (this.getVisitService().updateVisit(visit))
                            if(this.tests.isEmpty())
                              setTestsAvailable(false);
                        Message.message("Result submitted",FacesMessage.SEVERITY_INFO);
                        context.getExternalContext().getFlash().setKeepMessages(true);
                        setTestSelected(false);
                        lab=new Lab();
                        return ("/faces/lab/lab-report.xhtml?faces-redirect=true");
                    }
                }
            }else{
                Message.message("Result submitted already",FacesMessage.SEVERITY_INFO);
                context.getExternalContext().getFlash().setKeepMessages(true);
                return ("/faces/lab/lab-report.xhtml?faces-redirect=true");
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    public void patientForTheTest(){
        try {
            Visit visit=getVisitService().visitorToday(new Date());
            if (!visit.equals(null)){
                patient=visit.getPatient();
            }
            if(!patient.equals(null)){
                test=new Test(patient,new Date());
                tests=this.getLabService().getTestToBeDone(test);
                if(!tests.isEmpty())
                 this.setTestsAvailable(true);
                this.setPatientRecordAvailable(true);
            }
        }catch (Exception e){}
        return;
    }
    public void patientsInTheQueue(){
        try{
            visits=this.getVisitService().visitsToday(new Date());
            if (!visits.isEmpty()){
                labQueue=new ArrayList<>();
                for (Visit visit:visits){
                    if (visit.isSentToTheLab() && !visit.isInQueue()){
                        labQueue.add(visit);
                    }
                }
            }


        }catch (Exception e){
            System.out.println(e);
        }
    }
    public String selectedPatientRecords(){
        if(!visit.equals(null)){
            patient=visit.getPatient();
            setPatientRecordAvailable(true);
            return ("/faces/lab/lab-report.xhtml?faces-redirect=true");
        }
        return null;
    }
    public void testSelectedForSubmission(){
        if (this.test==null)
            Message.message("Error occurred During test selection",FacesMessage.SEVERITY_ERROR);
        else {
            this.setTestSelected(true);
            lab=new Lab(test.getTestNumber(),test.getTest(),test.getPatient());
        }
    }

    public List<Visit> getLabQueue() {
        return labQueue;
    }

    public void setLabQueue(List<Visit> labQueue) {
        this.labQueue = labQueue;
    }

    public String queueUrl(){
        return ("/faces/lab/lab-queue.xhtml");
    }

    private String generateRandomNumber(){
        return UUID.randomUUID().toString().replace("-","")
                .substring(1,16).toUpperCase();
    }

    public List<Test> getAvailableTests() {
        return availableTests;
    }

    public void setAvailableTests(List<Test> availableTests) {
        this.availableTests = availableTests;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public boolean isTestsAvailable() {
        return testsAvailable;
    }

    public void setTestsAvailable(boolean testsAvailable) {
        this.testsAvailable = testsAvailable;
    }

    public boolean isTestSelected() {
        return testSelected;
    }

    public VisitService getVisitService() {
        return visitService;
    }

    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    public void setTestSelected(boolean testSelected) {
        this.testSelected = testSelected;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public Set<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<String> numbers) {
        this.numbers = numbers;
    }

    public List<Lab> getLabResults() {
        return labResults;
    }

    public void setLabResults(List<Lab> labResults) {
        this.labResults = labResults;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LabService getLabService() {
        return labService;
    }

    public void setLabService(LabService labService) {
        this.labService = labService;
    }

    public boolean isPatientRecordAvailable() {
        return patientRecordAvailable;
    }

    public void setPatientRecordAvailable(boolean patientRecordAvailable) {
        this.patientRecordAvailable = patientRecordAvailable;
    }
}
