package egerton.hospital.model.patient;

import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.medication.Medication;
import egerton.hospital.model.triage.Triage;
import egerton.hospital.model.visit.Visit;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "checkIfExisting", query = "from Patient p where p.firstName=:firstname and p.lastName=:lastname and p.phone=:phone"),
        @NamedQuery(name = "triageActionList",query = "from Patient p join fetch p.triageList tr where tr.date=:date order by tr.time"),})
public class Patient  implements Serializable {
    private String patientNumber,nationalId,phone,email,address,firstName,lastName,gender,YoB;
    private byte[] image;
    private List<Visit> visits;
    private List<Triage>triageList;
    private List<Lab>labs;
    private List<Admission> admissions;
    private List<Medication>medications;

    public Patient() {
    }

    public Patient(String patientNumber, String nationalId, String phone, String email, String address, String firstName, String lastName, String gender, String yoB) {
        this.patientNumber = patientNumber;
        this.nationalId = nationalId;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        YoB = yoB;
    }

    public Patient(String patientNumber, String firstName, String lastName, String gender, String yoB) {
        this.patientNumber = patientNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        YoB = yoB;
    }

    public Patient(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    @Id
    @Column(name = "patient_number",length =15,unique = true)
    public String getPatientNumber() {
        return patientNumber;
    }
    @Column(name = "national_id",length =8,unique = true)
    public String getNationalId() {
        return nationalId;
    }
    @Column(name = "phone",length =10,nullable =false)
    public String getPhone() {
        return phone;
    }
    @Column(name = "email",length =100)
    public String getEmail() {
        return email;
    }
    @Column(name = "address",length =120)
    public String getAddress() {
        return address;
    }
    @Column(name = "first_name",length =15,nullable =false)
    public String getFirstName() {
        return firstName;
    }
    @Column(name = "last_name",length =15,nullable =false)
    public String getLastName() {
        return lastName;
    }
    @Column(name = "gender",length =7,nullable =false)
    public String getGender() {
        return gender;
    }
    @Column(name = "YoB",length =15,nullable =false)
    public String getYoB() {
        return YoB;
    }
    @Column(name = "photo")
    public byte[] getImage() {
        return image;
    }

    @OneToMany(mappedBy = "patient",targetEntity = Visit.class,cascade = CascadeType.ALL)
    public List<Visit> getVisits() {
        return visits;
    }
    @OneToMany(mappedBy = "patient",targetEntity = Triage.class,cascade = CascadeType.ALL)
    public List<Triage> getTriageList() {
        return triageList;
    }
    @OneToMany(mappedBy = "patient",targetEntity = Lab.class,cascade = CascadeType.ALL)
    public List<Lab> getLabs() {
        return labs;
    }
    @OneToMany(mappedBy = "patient",targetEntity = Admission.class,cascade = CascadeType.ALL)
    public List<Admission> getAdmissions() {
        return admissions;
    }
    @OneToMany(mappedBy = "patient",targetEntity = Medication.class,cascade = CascadeType.ALL)
    public List<Medication> getMedications() {
        return medications;
    }

    public void setLabs(List<Lab> labs) {
        this.labs = labs;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public void setTriageList(List<Triage> triageList) {
        this.triageList = triageList;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setYoB(String yoB) {
        YoB = yoB;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
