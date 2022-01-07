package data.repository;


import data.entity.Adres;
import data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    List<Adres> findAll() throws SQLException;
    Adres save(Adres adres) throws SQLException;
    Adres update(Adres adres) throws SQLException;
    boolean delete(int id) throws SQLException;
    Adres findById(int id) throws SQLException;
    Adres findByReiziger(Reiziger reiziger);
}
