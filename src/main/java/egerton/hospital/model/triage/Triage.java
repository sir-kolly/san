package egerton.hospital.model.triage;

import egerton.hospital.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "checkIfTriageRecordIsAlreadySaved",query = "from Triage tr join fetch tr.patient p where tr.date=:date and p.patientNumber=:number"),
        @NamedQuery(name = "getTriageResult",query = "from Triage tr join fetch tr.patient p where p.patientNumber=:number and tr.date=:date")})

public class Triage {
    private String recordNo,weight,pressure;
    private Date date,time;
    private Patient patient;

    public Triage() {
    }

    public Triage(String recordNo, String weight, String pressure, Date date, Date time, Patient patient) {
        this.recordNo = recordNo;
        this.weight = weight;
        this.pressure = pressure;
        this.date = date;
        this.time = time;
        this.patient = patient;
    }

    public Triage(String recordNo, Date date, Date time, Patient patient) {
        this.recordNo = recordNo;
        this.date = date;
        this.time = time;
        this.patient = patient;
    }

    public Triage(Date date,Patient patient) {
        this.date = date;
        this.patient = patient;
    }


    @Id
    @Column(name = "record_number",length = 15,nullable = false,unique = true)
    public String getRecordNo() {
        return recordNo;
    }
    @Column(name = "weight",length = 15,nullable = false)
    public String getWeight() {
        return weight;
    }
    @Column(name = "pressure",length = 50,nullable = false)
    public String getPressure() {
        return pressure;
    }
    @Column(name = "date",nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }
    @Column(name = "time",nullable = false)
    @Temporal(TemporalType.TIME)
    public Date getTime() {
        return time;
    }

    @ManyToOne
    @JoinColumn(name = "patient_number",foreignKey = @ForeignKey(name = "PATIENT_TRIAGE_FK"))
    public Patient getPatient() {
        return patient;
    }

    @Override
    public String toString() {
        return ("record-no:"+this.getRecordNo()+" weight:"+this.getWeight()+" pressure:"+this.getPressure()+
                " date:"+this.getDate()+" time:"+this.getTime()+" p-no:"+this.getPatient().getPatientNumber());
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
