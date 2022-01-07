package data.repository;



import data.entity.OVChipkaart;
import data.entity.Product;
import data.entity.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    OVChipkaart findById(int kaartNumber);
    OVChipkaart save(OVChipkaart ovChipkaart);
    OVChipkaart update(OVChipkaart ovChipkaart);
    boolean delete(int kaartNummer);
    List<Integer> getKaartNummersVanProduct(Product product);
    OVChipkaart addProductToOvchipKaart(OVChipkaart ovChipkaart, int productNumber);
    boolean removeProductFromOvChipkaart(OVChipkaart ovChipkaart, int productNumber);
}
