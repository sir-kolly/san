package egerton.hospital.service.room.impl;

import egerton.hospital.dao.room.RoomDAO;
import egerton.hospital.model.room.Bed;
import egerton.hospital.model.room.Room;
import egerton.hospital.service.room.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class RoomServiceImpl implements RoomService {
    @Inject
    private RoomDAO room;
    @Override
    public Set<String> freeRooms() {
        return this.getRoom().freeRooms();
    }

    @Override
    public Set<String> freeBeds(Room room) {
        return this.getRoom().freeBeds(room);
    }

    @Override
    public Bed bedInfo(Bed bed) {
        return this.getRoom().bedInfo(bed);
    }

    @Override
    public boolean updateRoom(Room room) {
        return this.getRoom().updateRoom(room);
    }

    @Override
    public boolean updateBed(Bed bed) {
        return this.getRoom().updateBed(bed);
    }

    public RoomDAO getRoom() {
        return room;
    }

    public void setRoom(RoomDAO room) {
        this.room = room;
    }
}
