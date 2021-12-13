package P1.data.repository;

import P1.data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface IReizigerDao {
    List<Reiziger> findAll();
    Reiziger findById(int id) throws SQLException;
    Reiziger save(Reiziger reiziger);
    Reiziger update(Reiziger reiziger);
    boolean delete(int id);
}
