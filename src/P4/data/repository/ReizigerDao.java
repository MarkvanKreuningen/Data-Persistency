package P4.data.repository;

import P4.data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDao {
    List<Reiziger> findAll() throws SQLException;
    Reiziger findById(int id) throws SQLException;
    List<Reiziger> findByGbdatum(String datum) throws SQLException;
    Reiziger save(Reiziger reiziger) throws SQLException;
    Reiziger update(Reiziger reiziger) throws SQLException;
    boolean delete(int id) throws SQLException;
}
