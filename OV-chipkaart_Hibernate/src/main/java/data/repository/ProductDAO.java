package data.repository;

import data.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    Product save(Product product) throws SQLException;
    Product update(Product product) throws SQLException;
    boolean delete(int id) throws SQLException;
    Product findById(int id) throws SQLException;
    List<Product> findAll();
}
