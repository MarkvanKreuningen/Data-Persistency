package P3.data.repository;

import P3.data.PostgresBaseDao;
import P3.data.entity.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDaoPsql extends PostgresBaseDao implements ReizigerDao {
    private final AdresDAO adresDAO = new AdresDAOPsql();

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> allReizigers = new ArrayList<>();
        try (Connection conn = super.getConnection()){
            String query = "select * from reiziger";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Reiziger r = new Reiziger();
                r.setId(rs.getInt("reiziger_id"));
                r.setVoorletters(rs.getString("voorletters"));
                r.setTussenvoegsel(rs.getString("tussenvoegsel"));
                r.setAchternaam(rs.getString("achternaam"));
                r.setGeboortedatum(LocalDate.parse(rs.getString("geboortedatum")));
                r.setAdres(adresDAO.findByReiziger(r.getId()));
                allReizigers.add(r);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allReizigers;
    }

    @Override
    public Reiziger findById(int id) {
        String query = "select * from reiziger where reiziger_id = ?";
        Reiziger r = new Reiziger();
        try (Connection conn = super.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                r.setId(rs.getInt("reiziger_id"));
                r.setVoorletters(rs.getString("voorletters"));
                r.setTussenvoegsel(rs.getString("tussenvoegsel"));
                r.setAchternaam(rs.getString("achternaam"));
                r.setGeboortedatum(LocalDate.parse(rs.getString("geboortedatum")));
                r.setAdres(adresDAO.findByReiziger(id));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return r;
    }

    @Override
    public Reiziger save(Reiziger r) {
        String query = "insert into reiziger values (?, ?, ?, ?, ?)";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, r.getId());
            pstmt.setString(2, r.getVoorletters());
            pstmt.setString(3, r.getTussenvoegsel());
            pstmt.setString(4, r.getAchternaam());
            pstmt.setDate(5, Date.valueOf(r.getGeboortedatum()));
            pstmt.executeUpdate();
            pstmt.close();
            return r;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Reiziger update(Reiziger r) {
        String query = "update reiziger set voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? where reiziger_id = ?";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, r.getVoorletters());
            pstmt.setString(2, r.getTussenvoegsel());
            pstmt.setString(3, r.getAchternaam());
            pstmt.setDate(4, Date.valueOf(r.getGeboortedatum()));
            pstmt.setInt(5, r.getId());
            pstmt.executeUpdate();
            pstmt.close();
            return r;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        String query = "delete from reiziger where reiziger_id = ?";
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
}
