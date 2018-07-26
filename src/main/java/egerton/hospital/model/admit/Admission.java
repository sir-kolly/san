package egerton.hospital.model.admit;

import egerton.hospital.model.employe.Employee;
import egerton.hospital.model.illness.Illness;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.room.Bed;
import egerton.hospital.model.room.Room;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "patientAdmissionDetails",query = "from Admission adm join fetch adm.patient p where p.patientNumber=:number")})
public class Admission {
    private String admissionNumber,section;
    private Date date,time;
    private Room room;
    private Bed bed;
    private Patient patient;
    private Employee doc;

    public Admission() {
    }

    public Admission(String admissionNumber, String section, Date date,Date time, Room room, Bed bed, Patient patient, Employee doc) {
        this.admissionNumber = admissionNumber;
        this.section = section;
        this.date = date;
        this.time=time;
        this.room = room;
        this.bed = bed;
        this.patient = patient;
        this.doc = doc;
    }

    @Id
    @Column(name = "admission_number",length = 15,nullable = false)
    public String getAdmissionNumber() {
        return admissionNumber;
    }
    @Column(name = "section",length = 50,nullable = false)
    public String getSection() {
        return section;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "date",nullable = false)
    public Date getDate() {
        return date;
    }
    @Temporal(TemporalType.TIME)
    @Column(name = "time",nullable = false)
    public Date getTime() {
        return time;
    }

    @ManyToOne
    @JoinColumn(name = "room",nullable = false,foreignKey = @ForeignKey(name = "RM_AD_FK"))
    public Room getRoom() {
        return room;
    }
    @ManyToOne
    @JoinColumn(name = "bed",nullable = false,foreignKey = @ForeignKey(name = "BD_AD_FK"))
    public Bed getBed() {
        return bed;
    }
    @ManyToOne
    @JoinColumn(name = "patient_number",nullable = false,foreignKey = @ForeignKey(name = "P_AD_FK"))
    public Patient getPatient() {
        return patient;
    }
    @ManyToOne
    @JoinColumn(name = "doc_number",nullable = false,foreignKey = @ForeignKey(name = "DOC_AD_FK"))
    public Employee getDoc() {
        return doc;
    }

    public void setDoc(Employee doc) {
        this.doc = doc;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

}
