package egerton.hospital.model.medication;

import egerton.hospital.model.employe.Employee;
import egerton.hospital.model.patient.Patient;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table
@NamedQueries({@NamedQuery(name = "patientMedications",query = "from Medication md join fetch md.patient p where p.patientNumber=:number")})
public class Medication {
    private String productNumber, productName,prescription,strength,routeOfAdministration,comment;
    private Date dateOfAdministration;
    private Patient patient;
    private Employee doc;

    public Medication() {
    }

    public Medication(String productNumber, String productName, String prescription, String strength, String routeOfAdministration,
                      String comment, Date dateOfAdministration, Patient patient, Employee doc) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.prescription = prescription;
        this.strength = strength;
        this.routeOfAdministration = routeOfAdministration;
        this.comment = comment;
        this.dateOfAdministration = dateOfAdministration;
        this.patient = patient;
        this.doc = doc;
    }

    @Id
    @Column(name = "product_number",length = 15,nullable = false)
    public String getProductNumber() {
        return productNumber;
    }
    @Column(name = "product_name",length = 150,nullable = false)
    public String getProductName() {
        return productName;
    }
    @Column(name = "prescription",length =150,nullable = false)
    public String getPrescription() {
        return prescription;
    }
    @Column(name = "strength",length = 50,nullable = false)
    public String getStrength() {
        return strength;
    }
    @Column(name = "route_of_administration",length = 25,nullable = false)
    public String getRouteOfAdministration() {
        return routeOfAdministration;
    }
    @Column(name = "comment",length = 155,nullable = false)
    public String getComment() {
        return comment;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "date",nullable = false)
    public Date getDateOfAdministration() {
        return dateOfAdministration;
    }
    @ManyToOne
    @JoinColumn(name = "patient_number",foreignKey = @ForeignKey(name = "MD_P_FK"),nullable = false)
    public Patient getPatient() {
        return patient;
    }
    @ManyToOne
    @JoinColumn(name = "doc_number",foreignKey = @ForeignKey(name = "DOC_MD_FK"),nullable = false)
    public Employee getDoc() {
        return doc;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public void setRouteOfAdministration(String routeOfAdministration) {
        this.routeOfAdministration = routeOfAdministration;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDateOfAdministration(Date dateOfAdministration) {
        this.dateOfAdministration = dateOfAdministration;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoc(Employee doc) {
        this.doc = doc;
    }
}
