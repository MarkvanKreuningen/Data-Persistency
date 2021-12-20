package P5.data.repository;

import P5.data.PostgresBaseDao;
import P5.data.entity.OVChipkaart;
import P5.data.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql extends PostgresBaseDao implements ProductDao {
    @Override
    public Product save(Product p) {
        String query = "insert into product values (?, ?, ?, ?)";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, p.getProductNumber());
            pstmt.setString(2, p.getNaam());
            pstmt.setString(3, p.getBeschrijving());
            pstmt.setDouble(4, p.getPrijs());
            pstmt.executeUpdate();
            pstmt.close();
            return p;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Product update(Product p) {
        String query = "update product set naam = ?, beschrijving = ?, prijs = ? where product_nummer = ?";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(4, p.getProductNumber());
            pstmt.setString(1, p.getNaam());
            pstmt.setString(2, p.getBeschrijving());
            pstmt.setDouble(3, p.getPrijs());
            pstmt.executeUpdate();
            pstmt.close();
            return p;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        String query = "delete from product where product_nummer = ?";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            result = (pstmt.executeUpdate() == 1);
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        String query = "select p.product_nummer, p.prijs, p.naam, p.beschrijving from product p inner join ov_chipkaart_product ocp on p.product_nummer = ocp.product_nummer where ocp.kaart_nummer = ?";
        List<Product> products = new ArrayList<>();
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ovChipkaart.getKaartNummer());
            ResultSet rs = pstmt.executeQuery();
            OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOPsql();
            while (rs.next()) {
                Product p = new Product();
                p.setProductNumber(rs.getInt("product_nummer"));
                p.setPrijs(rs.getDouble("prijs"));
                p.setNaam(rs.getString("naam"));
                p.setBeschrijving(rs.getString("beschrijving"));
                p.setKaartnummers(ovChipkaartDAO.getKaartNummersVanProduct(p));
                products.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findAll() {
        String query = "select * from product";
        List<Product> products = new ArrayList<>();
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOPsql();
            while (rs.next()) {
                Product p = new Product();
                p.setProductNumber(rs.getInt("product_nummer"));
                p.setPrijs(rs.getDouble("prijs"));
                p.setNaam(rs.getString("naam"));
                p.setBeschrijving(rs.getString("beschrijving"));
                p.setKaartnummers(ovChipkaartDAO.getKaartNummersVanProduct(p));
                products.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }
}
