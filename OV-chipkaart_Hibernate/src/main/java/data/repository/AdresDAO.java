package data.repository;


import data.entity.Adres;
import data.entity.Reiziger;

import java.util.List;

public interface AdresDAO {
    List<Adres> findAll();
    Adres save(Adres adres);
    Adres update(Adres adres);
    boolean delete(int id) throws Exception;
    Adres findById(int id) throws Exception;
    Adres findByReiziger(Reiziger reiziger);
}
