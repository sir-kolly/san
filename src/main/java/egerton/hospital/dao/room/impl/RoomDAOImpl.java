package egerton.hospital.dao.room.impl;

import egerton.hospital.dao.room.RoomDAO;
import egerton.hospital.model.room.Bed;
import egerton.hospital.model.room.Room;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public class RoomDAOImpl implements RoomDAO {
    @Inject
    private SessionFactory sessionFactory;
    @Override
    public Set<String> freeRooms() {
        return new LinkedHashSet<>(this.getSessionFactory().getCurrentSession().createNamedQuery("freeRooms",String.class).getResultList());
    }

    @Override
    public Set<String> freeBeds(Room room) {
        return new LinkedHashSet<>(this.getSessionFactory().getCurrentSession().createNamedQuery("freeBeds",String.class)
                .setParameter("number",room.getRoomNumber()).getResultList());
    }

    @Override
    public Bed bedInfo(Bed bed) {
        return this.getSessionFactory().getCurrentSession().createNamedQuery("bedInfo",Bed.class)
                .setParameter("roomN",bed.getRoom().getRoomNumber())
                .setParameter("bed",bed.getBedNumber())
                .getSingleResult();
    }

    @Override
    public boolean updateRoom(Room room) {
        this.getSessionFactory().getCurrentSession().update(room);
        return true;
    }

    @Override
    public boolean updateBed(Bed bed) {
        this.getSessionFactory().getCurrentSession().update(bed);
        return false;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
