package egerton.hospital.model.illness;

import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.medication.Medication;
import egerton.hospital.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "patientIllnesses",query = "from Illness ill join fetch ill.patient p where p.patientNumber=:number"),
        @NamedQuery(name = "getTodayPatientIllnesses",query = "from Illness ill join fetch ill.patient p where p.patientNumber=:number and ill.date=:date"),
        @NamedQuery(name = "checkIfIllnessIsSavedAlready",query = "from Illness ill join fetch ill.patient p where p.patientNumber=:number and ill.date=:date and ill.illness=:illness")})
public class Illness {
    private String illnessNumber,illness;
    private Patient patient;
    private Date date;
    private Set<Medication> medications;
    private Admission admission;

    public Illness() {
    }

    public Illness(String illnessNumber, String illness, Patient patient) {
        this.illnessNumber = illnessNumber;
        illness = illness;
        this.patient = patient;
    }

    public Illness(Patient patient, Date date) {
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

    @OneToMany(mappedBy = "illness",targetEntity = Medication.class,cascade = CascadeType.ALL)
    public Set<Medication> getMedications() {
        return medications;
    }
    @ManyToOne
    @JoinColumn(name = "admission_number",nullable = false,foreignKey = @ForeignKey(name = "AD_ILL_KEY"))
    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public void setMedications(Set<Medication> medications) {
        this.medications = medications;
    }

    public void setIllnessNumber(String illnessNumber) {
        this.illnessNumber = illnessNumber;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
