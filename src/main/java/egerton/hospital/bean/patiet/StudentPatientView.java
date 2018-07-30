package egerton.hospital.bean.patiet;

import egerton.hospital.message.Message;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.service.reception.ReceptionService;
import egerton.hospital.utill.Utill;
import egerton.school.records.modal.Student;
import egerton.school.records.service.SchoolRecordsService;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;
@Named("studentPatientView")
public class StudentPatientView {
    private Patient patient;
    private Student student;
    private boolean studentFound;

    public StudentPatientView(){
        patient=new Patient();
        student=new Student();
    }

    @Inject
    private ReceptionService receptionService;
    @Inject
    private SchoolRecordsService recordsService;

    public String submit(){
        try {
            Patient p=this.updatePatientInfo();
            p=this.getReceptionService().patientInfo(p);
            if(p!=null){
                patient=p;
                if(this.getReceptionService().save(this.patient)){
                    Message.message("Record update successful",FacesMessage.SEVERITY_INFO);
                    Utill.setNumber(patient.getPatientNumber());
                    return "student-reception-record-saved";
                }else{
                    Message.message("Error occurred",FacesMessage.SEVERITY_FATAL);
                    return null;
                }
            }else {
                Message.message("Record update successful",FacesMessage.SEVERITY_INFO);
                return "student-reception-record-saved";
            }

        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_INFO);
        }
        return null;
    }

    public String  getStudentRecord(Student student){
        student=this.getRecordsService().findStudentRecord(student);
        if(student==null){
            Message.message("No Record Found",FacesMessage.SEVERITY_WARN);
        }else {
            this.student=student;
            this.setStudentFound(true);
            return "student-record-found";
        }
        return null;
    }
    private String generatePatientNumber(){
        return UUID.randomUUID().toString().replace("-","")
                .substring(1,16).toUpperCase();
    }
    public void openModal(){
        PrimeFaces.current().ajax().update("form");
        PrimeFaces.current().executeScript("PF('dlg').show()");
    }

    private Patient updatePatientInfo(){
            this.patient.setFirstName(this.student.getFirstName());
            this.patient.setLastName(this.student.getLastName());
            this.patient.setGender(this.student.getGender());
            this.patient.setYoB(this.student.getYoB());
            this.patient.setPhone(this.student.getPhone());
            this.patient.setNationalId(this.student.getNationalId());
            this.patient.setAddress(this.student.getAddress());
            this.patient.setEmail(this.student.getEmail());
            this.patient.setPatientNumber(this.generatePatientNumber());
            return this.patient;
    }

    public String toStudentRecordDisplay(){
        return "student.xhtml";
    }


    public boolean isStudentFound() {
        return studentFound;
    }

    public void setStudentFound(boolean studentFound) {
        this.studentFound = studentFound;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ReceptionService getReceptionService() {
        return receptionService;
    }

    public void setReceptionService(ReceptionService receptionService) {
        this.receptionService = receptionService;
    }

    public SchoolRecordsService getRecordsService() {
        return recordsService;
    }

    public void setRecordsService(SchoolRecordsService recordsService) {
        this.recordsService = recordsService;
    }
}
