package P4.data.repository;

import P4.data.entity.OVChipkaart;
import P4.data.entity.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    OVChipkaart save(OVChipkaart ovChipkaart);
    OVChipkaart update(OVChipkaart ovChipkaart);
    boolean delete(int kaartNummer);
}
