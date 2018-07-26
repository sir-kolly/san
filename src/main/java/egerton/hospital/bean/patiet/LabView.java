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

    private boolean testSelected,testsAvailable;
    @Inject
    private LabService labService;
    @Inject
    private VisitService visitService;

    private List<Test> tests,availableTests;
    private List<Lab> labResults;
    private List<Visit>visits;
    private Set<String>numbers;

    public LabView(){
        lab=new Lab();
        test=new Test();
        patient=new Patient();
    }

    public String saveLabResult(){
        FacesContext context=FacesContext.getCurrentInstance();
        try {
            lab=new Lab(lab.getTestNumber(),lab.getTest(),lab.getResult(),lab.getComment(),new Date(),getPatient());
            if(this.getLabService().submitLabResult(lab)){
                Message.message("Result submitted",FacesMessage.SEVERITY_INFO);
                context.getExternalContext().getFlash().setKeepMessages(true);
                return "lab-result-saved";
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_INFO);
        }
        return null;
    }
    public List<Lab> labTestResult(){
        try {
            labResults=this.getLabService().getLabReport(patient);
            if(labResults.isEmpty());
            else return labResults;
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_INFO);
        }
        return null;
    }

    public Set<String> patientsForTheTest(){
        numbers=new HashSet<>();
        try {
            test=new Test(new Date());
            tests=getLabService().getTestToBeDone(test);
            if(!tests.isEmpty()){
                for (int i=0;i<tests.size();i++){
                    numbers.add(tests.get(i).getPatient().getPatientNumber());
                }
                return numbers;

            }
        }catch (Exception e){}
        return null;
    }

    public Patient patientInfo(){
        try {
            availableTests=new ArrayList<>();
            if(!tests.isEmpty()){
                for (int i=0;i<tests.size();i++){
                    String number=tests.get(i).getPatient().getPatientNumber();
                    String num=patient.getPatientNumber();
                    if(number.equalsIgnoreCase(num)){
                        patient=tests.get(i).getPatient();
                        availableTests.add(tests.get(i));
                    }
                }
                System.out.println(availableTests.size());
                this.setTestsAvailable(true);
                PrimeFaces.current().ajax().update("info");
                PrimeFaces.current().ajax().update("testForm");
                return patient;
            }
        }catch (Exception e){
        }
        return null;
    }

    public void testSelectedForSubmission(){
        if (this.test==null)
            Message.message("Error occurred During test selection",FacesMessage.SEVERITY_ERROR);
        else {
            this.setTestSelected(true);
            lab=new Lab(test.getTestNumber(),test.getTest(),test.getPatient());

            PrimeFaces.current().ajax().update("testRecordForm");
        }
    }

    private Timer getTimer(){
        return new Timer();
    }


    public String testToBeDoneUrl(){
        return ("/doctor/test.xhtml");
    }
    public String labReportUrl(){
        return ("/doctor/lab-report.xhtml");
    }
    public String newLabReportUrl(){
        return ("/lab/lab-test.xhtml");
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
}
