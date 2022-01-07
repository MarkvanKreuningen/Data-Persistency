package data.repository;



import data.entity.OVChipkaart;
import data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    OVChipkaart findById(int kaartNumber) throws SQLException;
    OVChipkaart save(OVChipkaart ovChipkaart) throws SQLException;
    OVChipkaart update(OVChipkaart ovChipkaart) throws SQLException;
    boolean delete(int kaartNummer) throws SQLException;
}
