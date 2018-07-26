package egerton.school.records.modal;

import egerton.school.records.util.Basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Student extends Basic {
    private String recordNo,regNo,faculty,dept;

    public Student() {
    }
    @Column(name = "record_no",length = 15,nullable = false,unique = true)
    public String getRecordNo() {
        return recordNo;
    }
    @Id
    @Column(name = "reg_no",length = 15,nullable = false,unique = true)
    public String getRegNo() {
        return regNo;
    }
    @Column(name = "faculty",length = 50,nullable = false)
    public String getFaculty() {
        return faculty;
    }
    @Column(name = "dept",length = 50,nullable = false)
    public String getDept() {
        return dept;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
