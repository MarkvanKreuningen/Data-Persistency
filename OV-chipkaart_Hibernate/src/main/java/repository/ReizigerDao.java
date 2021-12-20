package P4.data.repository;

import P4.data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDao {
    List<Reiziger> findAll();
    Reiziger findById(int id) throws SQLException;
    List<Reiziger> findByGbdatum(String datum);
    Reiziger save(Reiziger reiziger);
    Reiziger update(Reiziger reiziger);
    boolean delete(int id);
}
