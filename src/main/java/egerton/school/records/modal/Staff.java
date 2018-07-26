package egerton.school.records.modal;

import egerton.school.records.util.Basic;

import javax.persistence.Column;
import javax.persistence.Id;

public class Staff extends Basic{
    private String ssNo,department,role;

    public Staff() {
    }

    @Id
    @Column(name = "ss_no",length = 15,nullable = false,unique = true)
    public String getSsNo() {
        return ssNo;
    }
    @Column(name = "dept",length = 50,nullable = false)
    public String getDepartment() {
        return department;
    }
    @Column(name = "role",length = 60,nullable = false)
    public String getRole() {
        return role;
    }

    public void setSsNo(String ssNo) {
        this.ssNo = ssNo;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
