package repository;


import entity.OVChipkaart;
import entity.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    OVChipkaart save(OVChipkaart ovChipkaart);
    OVChipkaart update(OVChipkaart ovChipkaart);
    boolean delete(int kaartNummer);
}
