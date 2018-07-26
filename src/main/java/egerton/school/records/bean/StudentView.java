package egerton.school.records.bean;

import egerton.hospital.message.Message;
import egerton.school.records.modal.Student;
import egerton.school.records.service.SchoolRecordsService;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@Named("student")
@ViewScoped
public class StudentView implements Serializable {
    private Student student;
    @Inject
    private SchoolRecordsService schoolRecordsService;

    public StudentView(){
        student=new Student();
    }

    public void Save(){
      try {
          this.student.setRecordNo(this.generateRecordNo());

          if(this.schoolRecordsService.studentSaved(student)){
              Message.message("Saved",FacesMessage.SEVERITY_INFO);
          }

      }catch (Exception e){
          Message.message(""+e,FacesMessage.SEVERITY_FATAL);
      }
    }


    private String generateRecordNo(){
        return UUID.randomUUID().toString().replace("-","")
                .substring(1,16).toUpperCase();
    }
    public Student getStudent() {
        return student;
    }

    public SchoolRecordsService getSchoolRecordsService() {
        return schoolRecordsService;
    }

    public void setSchoolRecordsService(SchoolRecordsService schoolRecordsService) {
        this.schoolRecordsService = schoolRecordsService;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
