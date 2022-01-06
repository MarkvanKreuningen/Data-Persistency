package repository;



import entity.OVChipkaart;
import entity.Product;

import java.util.List;

public interface ProductDao {
    Product save(Product product);
    Product update(Product product);
    boolean delete(int id);
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();
}
