package P3.data.repository;

import P3.data.PostgresBaseDao;
import P3.data.entity.Adres;
import P3.data.entity.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql extends PostgresBaseDao implements AdresDAO {
    @Override
    public List<Adres> findAll() {
        List<Adres> adresList = new ArrayList<>();
        try (Connection conn = super.getConnection()) {
            String query = "select * from adres";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Adres a = new Adres();
                a.setId(rs.getInt("adres_id"));
                a.setPostcode(rs.getString("postcode"));
                a.setHuisnummer(rs.getString("huisnummer"));
                a.setStraat(rs.getString("straat"));
                a.setWoonplaats(rs.getString("woonplaats"));
                a.setReizigerId(rs.getInt("reiziger_id"));
                adresList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adresList;
    }

    @Override
    public Adres save(Adres a) {
        String query = "insert into adres values (?, ?, ?, ?, ?, ?)";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, a.getId());
            pstmt.setString(2, a.getPostcode());
            pstmt.setString(3, a.getHuisnummer());
            pstmt.setString(4, a.getStraat());
            pstmt.setString(5, a.getWoonplaats());
            pstmt.setInt(6, a.getReizigerId());
            pstmt.executeUpdate();
            pstmt.close();
            return a;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Adres update(Adres a) {
        String query = "update adres set postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? where adres_id = ?";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, a.getPostcode());
            pstmt.setString(2, a.getHuisnummer());
            pstmt.setString(3, a.getStraat());
            pstmt.setString(4, a.getWoonplaats());
            pstmt.setInt(5, a.getId());
            pstmt.executeUpdate();
            pstmt.close();
            return a;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        String query = "delete from adres where adres_id = ?";
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
    public Adres findById(int id) {
        String query = "select * from adres where adres_id = ?";
        Adres a = new Adres();
        try (Connection conn = super.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                a.setId(rs.getInt("adres_id"));
                a.setPostcode(rs.getString("postcode"));
                a.setHuisnummer(rs.getString("huisnummer"));
                a.setStraat(rs.getString("straat"));
                a.setWoonplaats(rs.getString("woonplaats"));
                a.setReizigerId(rs.getInt("reiziger_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return a;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        String query = "select * from adres where reiziger_id = ?";
        Adres a = new Adres();
        try (Connection conn = super.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                a.setId(rs.getInt("adres_id"));
                a.setPostcode(rs.getString("postcode"));
                a.setHuisnummer(rs.getString("huisnummer"));
                a.setStraat(rs.getString("straat"));
                a.setWoonplaats(rs.getString("woonplaats"));
                a.setReizigerId(reiziger.getId());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return a;
    }


}
