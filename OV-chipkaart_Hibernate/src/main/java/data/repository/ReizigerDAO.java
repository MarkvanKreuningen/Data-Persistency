package data.repository;



import data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    List<Reiziger> findAll();
    Reiziger findById(int id) throws SQLException;
    List<Reiziger> findByGbdatum(String datum);
    Reiziger save(Reiziger reiziger);
    Reiziger update(Reiziger reiziger);
    boolean delete(int id);
}
