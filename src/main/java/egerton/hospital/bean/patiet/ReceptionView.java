package egerton.hospital.bean.patiet;

import egerton.hospital.message.Message;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.visit.Visit;
import egerton.hospital.service.patient.PatientService;
import egerton.hospital.service.visit.VisitService;
import egerton.hospital.utill.Utill;
import egerton.school.records.modal.Staff;
import egerton.school.records.modal.Student;
import egerton.school.records.service.SchoolRecordsService;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@Named
@ViewScoped
public class ReceptionView implements Serializable {
    private Patient patient;
    private Student student;
    private Integer actionID;
    private Staff staff;
    private Visit visit;
    private boolean studentFound,patientVisitsRecordAvailable;

    @Inject
    private PatientService patientService;
    @Inject
    private SchoolRecordsService recordsService;
    @Inject
    private VisitService visitService;

    private List<Visit>visits;

    public ReceptionView(){
        patient=new Patient();
        student=new Student();
        staff=new Staff();
        visit=new Visit();
    }

    public String savePatient(){
       try{
           Patient pat;
           switch (actionID){
               case 1:
                   patient=this.setStudentPatientRecord();
                   pat=this.getPatientService().checkIfExisting(patient)                                                                                  ;
                   if(pat==null){
                       if(this.getPatientService().save(patient)){
                           Message.message("Record Update successful",FacesMessage.SEVERITY_WARN);
                           Utill.setNumber(patient.getPatientNumber());
                           student=new Student();
                           setStudentFound(false);
                           return "patient-record-saved";
                       }
                   }else {
                       Message.message("Record Update successful",FacesMessage.SEVERITY_WARN);
                       this.patient=pat;
                       Utill.setNumber(patient.getPatientNumber());
                       student=new Student();
                       setStudentFound(false);
                       return "patient-record-saved";
                   }
                   break;
               case 2:
                   patient=this.setEmployeePatientRecord();
                   pat=this.getPatientService().checkIfExisting(patient)                                                                                  ;
                   if(pat==null){
                       if(this.getPatientService().save(patient)){
                           Message.message("Record Update successful",FacesMessage.SEVERITY_WARN);
                           Utill.setNumber(patient.getPatientNumber());
                           return "patient-record-saved";
                       }
                   }else {
                       this.patient=pat;
                       Message.message("Record Update successful",FacesMessage.SEVERITY_WARN);
                       Utill.setNumber(patient.getPatientNumber());
                       return "patient-record-saved";
                   }
                   break;
               case 3:
                   pat=this.getPatientService().checkIfExisting(patient)                                                                                  ;
                   if(pat==null){
                       if(this.getPatientService().save(patient)){
                           Message.message("Record Update successful",FacesMessage.SEVERITY_WARN);
                           Utill.setNumber(patient.getPatientNumber());
                           return "patient-record-saved";
                       }
                   }else {
                       Message.message("Record Update successful",FacesMessage.SEVERITY_WARN);
                       Utill.setNumber(patient.getPatientNumber());
                       return "patient-record-saved";
                   }
                   break;
           }
       }catch (Exception e){
           Message.message(""+e,FacesMessage.SEVERITY_INFO);
       }

        return null;
    }

    public String saveVisit(){
        FacesContext context=FacesContext.getCurrentInstance();
        visit=new Visit(generateRandomNumber(),new Date(),new Date(),false,patient);
        try {
            if(this.getVisitService().checkIfVisitIsAlreadyRecordedForPatient(visit)){
                Message.message("Record Submitted Successfully",FacesMessage.SEVERITY_INFO);
                context.getExternalContext().getFlash().setKeepMessages(true);
                return "visit-info-updated";
            }else {
                this.getVisitService().saveVisit(visit);
                Message.message("Record Submitted Successfully",FacesMessage.SEVERITY_INFO);
                context.getExternalContext().getFlash().setKeepMessages(true);
                return "visit-info-updated";
            }
        }catch (Exception e){
            context.getExternalContext().getFlash().setKeepMessages(true);
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    public Patient getPatientInfo(){
        try {
            patient=this.getPatientService().patientInfo(patient);
            if(patient!=null){
                return patient;
            }
        }catch (Exception e){}

        return new Patient();
    }
    private void updateForm(){
        PrimeFaces.current().ajax().update("formInfo");
    }

    private Patient setStudentPatientRecord(){
        return new Patient(this.generateRandomNumber(),this.student.getNationalId(),this.student.getPhone(),
                this.student.getEmail(),this.student.getAddress(),this.student.getFirstName(),this.student.getLastName(),
                this.student.getGender(),this.student.getYoB());
    }
    private Patient setEmployeePatientRecord(){
        return new Patient(this.generateRandomNumber(),this.staff.getNationalId(),this.staff.getPhone(),
                this.staff.getEmail(),this.staff.getAddress(),this.staff.getFirstName(),this.staff.getLastName(),
                this.staff.getGender(),this.staff.getYoB());
    }
    public String getStudentRecord(){
        try {
            student=this.getRecordsService().findStudentRecord(student);
            System.out.println(student.getDept());
            if(student!=null){
                setStudentFound(true);
                setActionID(1);
                PrimeFaces.current().ajax().update("form");
                PrimeFaces.current().ajax().update("modalForm");
                return "student-record-found";
            }else
                Message.message("Student not found",FacesMessage.SEVERITY_WARN);

        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }

        return null;
    }

    public void patientVisits(){
        visits=this.getVisitService().patientVisits();
    }
    private String generateRandomNumber(){
        return UUID.randomUUID().toString().replace("-","")
                .substring(1,16).toUpperCase();
    }

    public void openModal(){
        PrimeFaces.current().executeScript("PF('dlg').show()");
    }
    public String formatDate(){
        SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");
        return (sf.format(new Date()));
    }

    public String formatTime(){
        SimpleDateFormat sf=new SimpleDateFormat("HH:mm");
        return (sf.format(new Date()));
    }

    public String consultationUrl(){
        return ("/doctor/consultation.xhtml");
    }
    public String studentReceptionUrl(){
        return ("/reception/student-reception.xhtml");
    }
    public Integer getActionID() {
        return actionID;
    }

    public SchoolRecordsService getRecordsService() {
        return recordsService;
    }
    public void setRecordsService(SchoolRecordsService recordsService) {
        this.recordsService = recordsService;
    }
    public PatientService getPatientService() {
        return patientService;
    }

    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }
    public Patient getPatient() {
        return patient;
    }

    public Student getStudent() {
        return student;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setActionID(Integer actionID) {
        this.actionID = actionID;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
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

    public boolean isStudentFound() {
        return studentFound;
    }

    public void setStudentFound(boolean studentFound) {
        this.studentFound = studentFound;
    }

    public boolean isPatientVisitsRecordAvailable() {
        return patientVisitsRecordAvailable;
    }

    public void setPatientVisitsRecordAvailable(boolean patientVisitsRecordAvailable) {
        this.patientVisitsRecordAvailable = patientVisitsRecordAvailable;
    }
}
