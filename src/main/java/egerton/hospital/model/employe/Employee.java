package egerton.hospital.model.employe;

import egerton.hospital.model.medication.Medication;

import javax.persistence.*;
import java.util.List;
@Entity
@Table
@NamedQuery(name = "employeeInfo",query = "from Employee em where em.employeeNumber=:num and em.password=:pwd")
public class Employee {
    private String employeeNumber,nationalId,phone,email,firstName,lastName,gender,role,password;
    private List<Medication> medications;


    public Employee() {
    }

    public Employee(String employeeNumber, String nationalId, String phone, String email, String firstName, String lastName, String gender, String role) {
        this.employeeNumber = employeeNumber;
        this.nationalId = nationalId;
        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.role = role;
    }

    @Id
    @Column(name = "employee_number",length = 15,nullable = false)
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    @Column(name = "national_id",length = 8,nullable = false)
    public String getNationalId() {
        return nationalId;
    }
    @Column(name = "phone",length = 10,nullable = false)
    public String getPhone() {
        return phone;
    }
    @Column(name = "email",length = 100,nullable = false)
    public String getEmail() {
        return email;
    }
    @Column(name = "first_name",length = 15,nullable = false)
    public String getFirstName() {
        return firstName;
    }
    @Column(name = "last_name",length = 15,nullable = false)
    public String getLastName() {
        return lastName;
    }
    @Column(name = "gender",length = 7,nullable = false)
    public String getGender() {
        return gender;
    }
    @Column(name = "role",length = 25,nullable = false)
    public String getRole() {
        return role;
    }
    @Column(name = "password",length = 50,nullable = false)
    public String getPassword() {
        return password;
    }

    @OneToMany(mappedBy = "doc",cascade = CascadeType.ALL,targetEntity = Medication.class)
    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
