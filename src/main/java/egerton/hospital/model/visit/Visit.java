package egerton.hospital.model.visit;

import egerton.hospital.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "patientVisits",query = "from Visit vs join fetch vs.patient p where p.patientNumber=:number"),
        @NamedQuery(name = "visitsForTheTriageToday",query = "from Visit vs where vs.date=:d and vs.treated=false and vs.attendedTriage=false order by vs.time"),
        @NamedQuery(name = "visitsToday",query = "from Visit vs where vs.date=:d and vs.treated=false order by vs.time"),
        @NamedQuery(name ="checkIfVisitIsAlreadyRecordedForPatient",query ="from Visit vs join fetch vs.patient p where vs.date=:date and p.patientNumber=:number")})
public class Visit {
    private String visitNo;
    private Date date,time;
    private boolean treated,attendedTriage;
    private Patient patient;

    public Visit(){}

    public Visit(String visitNo, Date date, Date time,  boolean treated, Patient patient) {
        this.visitNo = visitNo;
        this.date = date;
        this.time = time;
        this.treated = treated;
        this.patient = patient;
    }

    public Visit(String visitNo, Date date, Date time, boolean treated, boolean attendedTriage, Patient patient) {
        this.visitNo = visitNo;
        this.date = date;
        this.time = time;
        this.treated = treated;
        this.attendedTriage = attendedTriage;
        this.patient = patient;
    }

    @Id
    @Column(name = "visit_number",length = 15,nullable = false)
    public String getVisitNo() {
        return visitNo;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    @Temporal(TemporalType.TIME)
    @Column(name = "time")
    public Date getTime() {
        return time;
    }
    @Column(name = "treated",nullable = false)
    public boolean isTreated() {
        return treated;
    }
    @Column(name = "attended_triage",nullable = false)
    public boolean isAttendedTriage() {
        return attendedTriage;
    }

    @ManyToOne
    @JoinColumn(name = "patient_number",foreignKey = @ForeignKey(name = "PATIENT_VISIT_FK"),nullable = false)
    public Patient getPatient() {
        return patient;
    }

    public void setAttendedTriage(boolean attendedTriage) {
        this.attendedTriage = attendedTriage;
    }

    public void setTreated(boolean treated) {
        this.treated = treated;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
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
