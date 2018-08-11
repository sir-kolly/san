package egerton.hospital.model.symptom;

import egerton.hospital.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table
@NamedQueries({@NamedQuery(name ="getTodaySymptom",query = "from Symptom s join fetch s.patient p where p.patientNumber=:numnber and s.date=:date")})
public class Symptom {
    private Date date;
    private String id,symptom;
    private Patient patient;

    public Symptom() {
    }

    @Id
    @Column(name = "id",nullable = false,length = 15)
    public String getId() {
        return id;
    }
    @Column(name = "symptoms",nullable = false,length = 450)
    public String getSymptom() {
        return symptom;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "date",nullable = false)
    public Date getDate() {
        return date;
    }

    public Symptom(Date date, String id, String symptom, Patient patient) {
        this.date = date;
        this.id = id;
        this.symptom = symptom;
        this.patient = patient;
    }

    @ManyToOne
    @JoinColumn(name = "patient_number",nullable = false,foreignKey = @ForeignKey(name = "SYP_PA_FK"))
    public Patient getPatient() {
        return patient;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
