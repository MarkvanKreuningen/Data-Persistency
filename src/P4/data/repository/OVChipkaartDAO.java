package P4.data.repository;

import P4.data.entity.OVChipkaart;
import P4.data.entity.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    OVChipkaart save(OVChipkaart ovChipkaart) throws SQLException;
    OVChipkaart update(OVChipkaart ovChipkaart) throws SQLException;
    boolean delete(int kaartNummer) throws SQLException;
}
