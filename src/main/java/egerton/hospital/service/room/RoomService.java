package egerton.hospital.service.room;

import egerton.hospital.model.room.Bed;
import egerton.hospital.model.room.Room;

import java.util.Set;

public interface RoomService {
    Set<String> freeRooms();
    Set<String>freeBeds(Room room);
    Bed bedInfo(Bed bed);
}
