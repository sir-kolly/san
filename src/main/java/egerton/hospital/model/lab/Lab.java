package egerton.hospital.model.lab;

import egerton.hospital.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "getAllLabReport",query = "from Lab l"),
        @NamedQuery(name = "previousReports",query = "from Lab l join fetch l.patient p where p.patientNumber=:number and l.date !=:date"),
        @NamedQuery(name = "todayLabReport",query = "from Lab l join fetch l.patient p where p.patientNumber=:number and l.date=:date"),
        @NamedQuery(name = "checkIfResultIsAlreadySubmitted",query = "from Lab l join fetch l.patient p where p.patientNumber=:num and l.date=:date and l.test=:test")})
public class Lab {
    private String testNumber,test,result,comment;
    private Date date;
    private Patient patient;

    public Lab() {
    }

    public Lab(String testNumber, String test, String result, String comment, Date date, Patient patient) {
        this.testNumber = testNumber;
        this.test = test;
        this.result = result;
        this.comment = comment;
        this.date = date;
        this.patient = patient;
    }

    public Lab(String testNumber, String test, Patient patient) {
        this.testNumber = testNumber;
        this.test = test;
        this.patient = patient;
    }

    public Lab(Date date, Patient patient) {
        this.date = date;
        this.patient = patient;
    }

    @Id
    @Column(name = "lab_test_number",length = 15,nullable = false)
    public String getTestNumber() {
        return testNumber;
    }
    @Column(name = "test",length = 150,nullable = false)
    public String getTest() {
        return test;
    }
    @Column(name = "result",length = 350,nullable = false)
    public String getResult() {
        return result;
    }
    @Column(name = "comment",length = 350,nullable = false)
    public String getComment() {
        return comment;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "date",length = 350,nullable = false)
    public Date getDate() {
        return date;
    }

    @ManyToOne
    @JoinColumn(name = "patient_number",nullable = false,foreignKey = @ForeignKey(name = "LAB_P_FK"))
    public Patient getPatient() {
        return patient;
    }

    public void setTestNumber(String testNumber) {
        this.testNumber = testNumber;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
