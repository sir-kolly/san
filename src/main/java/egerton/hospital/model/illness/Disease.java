package egerton.hospital.model.illness;

import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.medication.Medicate;
import egerton.hospital.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "patientIllnesses",query = "from Disease ds join fetch ds.patient p where p.patientNumber=:number"),
        @NamedQuery(name = "getTodayPatientIllnesses",query = "from Disease ds join fetch ds.patient p where p.patientNumber=:number and ds.date=:date"),
        @NamedQuery(name = "patientIllnessInfo",query = "from Disease ds join fetch ds.patient p where p.patientNumber=:number and ds.date=:date  and ds.illness=:ill"),
        @NamedQuery(name = "checkIfIllnessIsSavedAlready",query = "from Disease ds join fetch ds.patient p where p.patientNumber=:number and ds.date=:date and ds.illness=:ill")})
public class Disease {
    private String illnessNumber,illness;
    private Patient patient;
    private Date date;
    private List<Medicate> medications;
    private Admission admission;

    public Disease() {
    }

    public Disease(String illnessNumber, String illness, Patient patient, Date date) {
        this.illnessNumber = illnessNumber;
        this.illness = illness;
        this.patient = patient;
        this.date = date;
    }

    public Disease(String illness, Patient patient, Date date) {
        this.illness = illness;
        this.patient = patient;
        this.date = date;
    }

    public Disease(Patient patient, Date date) {
        this.patient = patient;
        this.date = date;
    }

    @Id
    @Column(name = "illness_number",length = 15,nullable = false)
    public String getIllnessNumber() {
        return illnessNumber;
    }
    @Column(name = "illness",length = 15,nullable = false)
    public String getIllness() {
        return illness;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "date",nullable = false)
    public Date getDate() {
        return date;
    }
    @ManyToOne
    @JoinColumn(name = "patient_number",nullable = false,foreignKey = @ForeignKey(name = "PT_ILL_KEY"))
    public Patient getPatient() {
        return patient;
    }
    @OneToMany(mappedBy = "disease",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public List<Medicate> getMedications() {
        return medications;
    }
    @ManyToOne
    @JoinColumn(name = "admission_number",foreignKey = @ForeignKey(name = "AD_ILL_KEY"))
    public Admission getAdmission() {
        return admission;
    }

    public void setIllnessNumber(String illnessNumber) {
        this.illnessNumber = illnessNumber;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMedications(List<Medicate> medications) {
        this.medications = medications;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }
}
