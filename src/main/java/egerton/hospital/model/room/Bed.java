package egerton.hospital.model.room;

import egerton.hospital.model.admit.Admission;

import javax.persistence.*;
import java.util.List;
@Entity
@Table
@NamedQueries({
        @NamedQuery(name = "freeBeds",query = "select b.bedNumber from Bed b join  b.room r where b.occupied=false and r.roomNumber=:number"),
        @NamedQuery(name = "bedInfo",query = "from Bed b join fetch b.room r where r.roomNumber=:roomN and b.bedNumber=:bed")})
public class Bed {
    private String recordNo, bedNumber;
    private boolean occupied;
    private Room room;
    private List<Admission> admissions;

    public Bed() {
    }

    public Bed(String recordNo, String bedNumber, boolean occupied, Room room, List<Admission> admissions) {
        this.recordNo = recordNo;
        this.bedNumber = bedNumber;
        this.occupied = occupied;
        this.room = room;
        this.admissions = admissions;
    }

    @Id
    @Column(name = "record_number",length = 15,nullable = false)
    public String getRecordNo() {
        return recordNo;
    }
    @Column(name = "bed_number",nullable = false)
    public String getBedNumber() {
        return bedNumber;
    }
    @Column(name = "occupied",nullable = false)
    public boolean isOccupied() {
        return occupied;
    }
    @ManyToOne
    @JoinColumn(name = "room_number",nullable = false,foreignKey = @ForeignKey(name = "R_B_FK"))
    public Room getRoom() {
        return room;
    }
    @OneToMany(mappedBy = "bed",targetEntity = Admission.class,cascade = CascadeType.ALL)
    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
