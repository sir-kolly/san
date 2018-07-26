package egerton.hospital.model.room;

import egerton.hospital.model.admit.Admission;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
@Entity
@Table
@NamedQueries(@NamedQuery(name = "freeRooms",query = "select r.roomNumber from Room r join  r.beds b where b.occupied=false and r.size>0"))
public class Room {
    private String roomNumber,size;
    private Set<Bed> beds;
    private List<Admission> admissions;

    public Room() {
    }


    @Id
    @Column(name = "room_number",length = 4,nullable = false)
    public String getRoomNumber() {
        return roomNumber;
    }
    @Column(name = "size",length = 1,nullable = false)
    public String getSize() {
        return size;
    }
    @OneToMany(mappedBy = "room",targetEntity = Bed.class,cascade = CascadeType.ALL)
    public Set<Bed> getBeds() {
        return beds;
    }
    @OneToMany(mappedBy = "room",targetEntity = Admission.class,cascade = CascadeType.ALL)
    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setBeds(Set<Bed> beds) {
        this.beds = beds;
    }
}
