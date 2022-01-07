package data.repository;

import data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    List<Reiziger> findAll() throws SQLException;
    Reiziger findById(int id) throws SQLException;
    Reiziger save(Reiziger reiziger) throws SQLException;
    Reiziger update(Reiziger reiziger) throws SQLException;
    boolean delete(int id) throws SQLException;
}
