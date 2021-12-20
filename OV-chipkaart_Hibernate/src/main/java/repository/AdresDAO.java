package P4.data.repository;

import P4.data.entity.Adres;
import P4.data.entity.Reiziger;

import java.util.List;

public interface AdresDAO {
    List<Adres> findAll();
    Adres save(Adres adres);
    Adres update(Adres adres);
    boolean delete(int id);
    Adres findById(int id);
    Adres findByReiziger(Reiziger reiziger);
}
