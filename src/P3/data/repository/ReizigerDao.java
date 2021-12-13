package P3.data.repository;

import P3.data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDao {
    List<Reiziger> findAll();
    Reiziger findById(int id) throws SQLException;
    Reiziger save(Reiziger reiziger);
    List<Reiziger> findByGbdatum(String datum);
    Reiziger update(Reiziger reiziger);
    boolean delete(int id);
}
