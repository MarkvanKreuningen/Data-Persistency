package P4.data.repository;

import P4.data.PostgresBaseDao;
import P4.data.entity.Adres;
import P4.data.entity.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql extends PostgresBaseDao implements AdresDAO {
    @Override
    public List<Adres> findAll() throws SQLException {
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
            throw new SQLException(e.toString());
        }
        return adresList;
    }

    @Override
    public Adres save(Adres a) throws SQLException {
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
        } catch (SQLException e) {
            throw new SQLException(e.toString());
        }
    }

    @Override
    public Adres update(Adres a) throws SQLException {
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
        } catch (SQLException e) {
            throw new SQLException(e.toString());
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean result = false;
        String query = "delete from adres where adres_id = ?";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            result = (pstmt.executeUpdate() == 1);
            pstmt.close();
        } catch (SQLException e) {
            throw new SQLException(e.toString());
        }
        return result;
    }

    @Override
    public Adres findById(int id) throws SQLException {
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
        } catch (SQLException e) {
            throw new SQLException(e.toString());
        }
        return a;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
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
                a.setReizigerId(rs.getInt("reiziger_id"));
            }
        } catch (SQLException e) {
            throw new SQLException(e.toString());
        }
        return a;
    }
}
