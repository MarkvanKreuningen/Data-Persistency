package data.repository;


import data.entity.Adres;
import data.entity.Reiziger;

import java.util.List;

public interface AdresDAO {
    List<Adres> findAll();
    Adres save(Adres adres);
    Adres update(Adres adres);
    boolean delete(int id);
    Adres findById(int id);
    Adres findByReiziger(Reiziger reiziger);
}