package egerton.hospital.model.test;

import egerton.hospital.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "getTestToBeDone",query = "from Test t where t.date=:date and t.testDone=false"),
        @NamedQuery(name = "checkIfTestIsAlreadySubmitted",query = "from Test t where t.date=:date and t.test=:test")})
public class Test {
    private String testNumber,test;
    private Patient patient;
    private boolean testDone;
    private Date date;

    public Test() {
    }

    public Test(String testNumber, String test, Patient patient, boolean testDone, Date date) {
        this.testNumber = testNumber;
        this.test = test;
        this.patient = patient;
        this.testDone = testDone;
        this.date = date;
    }

    public Test(Date date) {
        this.date = date;
    }

    @Id
    @Column(name = "test_number",length = 15,nullable = false)
    public String getTestNumber() {
        return testNumber;
    }
    @Column(name = "test",length = 150,nullable = false)
    public String getTest() {
        return test;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "date",nullable = false)
    public Date getDate() {
        return date;
    }
    @Column(name = "test_done",nullable = false)
    public boolean isTestDone() {
        return testDone;
    }

    @ManyToOne
    @JoinColumn(name = "patient_number",foreignKey = @ForeignKey(name = "P_T_FK"),nullable = false)
    public Patient getPatient() {
        return patient;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTestNumber(String testNumber) {
        this.testNumber = testNumber;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public void setTestDone(boolean testDone) {
        this.testDone = testDone;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
