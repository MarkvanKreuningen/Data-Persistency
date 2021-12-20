package P5.data.repository;

import P5.data.entity.OVChipkaart;
import P5.data.entity.Product;

import java.util.List;

public interface ProductDao {
    Product save(Product product);
    Product update(Product product);
    boolean delete(int id);
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();
}
