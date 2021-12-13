package P2.data.repository;

import P2.data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDao {
    List<Reiziger> findAll();
    Reiziger findById(int id);
    Reiziger save(Reiziger reiziger);
    Reiziger update(Reiziger reiziger);
    boolean delete(int id);
}
