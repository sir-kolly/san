package egerton.hospital.model.medication;

import egerton.hospital.model.employe.Employee;
import egerton.hospital.model.illness.Disease;
import egerton.hospital.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "patientMedications",query = "from Medicate md join fetch md.patient p where p.patientNumber=:number"),
        @NamedQuery(name = "checkIfMedicationIsSavedAlready",query = "from Medicate md join fetch md.patient p join fetch md.disease ds where p.patientNumber=:number and md.medName=:name and ds.illnessNumber=:illno")})
public class Medicate {
    private String medNumber, medName,prescription,strength,mode,diagnosis,recommendation,illness;
    private Date date;
    private Patient patient;
    private Employee doc;
    private Disease disease;

    public Medicate(){}

    public Medicate(String s, String medName, String diagnosis, String illness, String prescription, String strength, String mode, String recommendation, Date date, Patient patient, Employee employee) {
        this.medNumber=s;
        this.medName=medName;
        this.diagnosis=diagnosis;
        this.illness=illness;
        this.prescription=prescription;
        this.strength=strength;
        this.mode=mode;
        this.recommendation=recommendation;
        this.date=date;
        this.patient=patient;
        this.doc=employee;
    }

    @Id
    @Column(name = "medicine_number",length = 15,nullable = false)
    public String getMedNumber() {
        return medNumber;
    }
    @Column(name = "medicine_name",length = 150,nullable = false)
    public String getMedName() {
        return medName;
    }
    @Column(name = "description",length = 250,nullable = false)
    public String getPrescription() {
        return prescription;
    }
    @Column(name = "strength",length = 155,nullable = false)
    public String getStrength() {
        return strength;
    }
    @Column(name = "mode",length = 50,nullable = false)
      public String getMode() {
        return mode;
    }

    @Column(name = "diagnosis",length = 550,nullable = false)
    public String getDiagnosis() {
        return diagnosis;
    }
    @Column(name = "recommendation",length = 350,nullable = false)
    public String getRecommendation() {
        return recommendation;
    }
    @Column(name = "illness",length = 150,nullable = false)
    public String getIllness() {
        return illness;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date",nullable = false)
    public Date getDate() {
        return date;
    }
    @ManyToOne
    @JoinColumn(name = "patient_number",nullable = false,foreignKey = @ForeignKey(name = "MD_PK_FK"))
    public Patient getPatient() {
        return patient;
    }
    @ManyToOne
    @JoinColumn(name = "doc_number",nullable = false,foreignKey = @ForeignKey(name = "DOC_PK_FK"))
    public Employee getDoc() {
        return doc;
    }
    @ManyToOne
    @JoinColumn(name = "illness_number",nullable = false,foreignKey = @ForeignKey(name = "DS_PK_FK"))
    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public void setMedNumber(String medNumber) {
        this.medNumber = medNumber;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
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

    public void setDoc(Employee doc) {
        this.doc = doc;
    }

}
