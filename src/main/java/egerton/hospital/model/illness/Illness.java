package egerton.hospital.model.illness;

import egerton.hospital.model.patient.Patient;

import javax.persistence.*;

@Entity
@Table
@NamedQueries({@NamedQuery(name = "patientIllnesses",query = "from Illness ill join fetch ill.patient p where p.patientNumber=:number")})
public class Illness {
    private String illnessNumber,Illness;
    private Patient patient;

    public Illness() {
    }

    public Illness(String illnessNumber, String illness, Patient patient) {
        this.illnessNumber = illnessNumber;
        Illness = illness;
        this.patient = patient;
    }
    @Id
    @Column(name = "illness_number",length = 15,nullable = false)
    public String getIllnessNumber() {
        return illnessNumber;
    }
    @Column(name = "illness",length = 15,nullable = false)
    public String getIllness() {
        return Illness;
    }
    @ManyToOne
    @JoinColumn(name = "patient_number",nullable = false,foreignKey = @ForeignKey(name = "PT_ILL_KEY"))
    public Patient getPatient() {
        return patient;
    }


    public void setIllnessNumber(String illnessNumber) {
        this.illnessNumber = illnessNumber;
    }

    public void setIllness(String illness) {
        Illness = illness;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
