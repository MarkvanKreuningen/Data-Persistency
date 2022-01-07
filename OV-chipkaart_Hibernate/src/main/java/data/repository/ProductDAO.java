package data.repository;



import data.entity.OVChipkaart;
import data.entity.Product;

import java.util.List;

public interface ProductDAO {
    Product save(Product product);
    Product update(Product product);
    boolean delete(int id);
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();
}
