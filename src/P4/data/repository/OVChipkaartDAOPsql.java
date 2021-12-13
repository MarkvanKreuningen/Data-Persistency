package P4.data.repository;

import P2.data.PostgresBaseDao;
import P4.data.entity.OVChipkaart;
import P4.data.entity.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql extends PostgresBaseDao implements OVChipkaartDAO {
    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        String query = "select * from ov_chipkaart where reiziger_id = ?";
        List<OVChipkaart> ovChipkaarts = new ArrayList<>();
        try (Connection conn = super.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                OVChipkaart o = new OVChipkaart();
                o.setKaartNummer(rs.getInt("kaart_nummer"));
                o.setGeldigTot(LocalDate.parse(rs.getString("geldig_tot")));
                o.setKlasse(rs.getInt("klasse"));
                o.setSaldo(rs.getDouble("saldo"));
                o.setReiziger(reiziger);
                ovChipkaarts.add(o);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ovChipkaarts;
    }

    @Override
    public OVChipkaart save(OVChipkaart o) {
        String query = "insert into ov_chipkaart values (?, ?, ?, ?, ?)";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, o.getKaartNummer());
            pstmt.setDate(2, Date.valueOf(o.getGeldigTot()));
            pstmt.setInt(3, o.getKlasse());
            pstmt.setDouble(4, o.getSaldo());
            pstmt.setInt(5, o.getReiziger().getId());
            pstmt.executeUpdate();
            pstmt.close();
            return o;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public OVChipkaart update(OVChipkaart o) {
        String query = "update ov_chipkaart set geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? where kaart_nummer = ?";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, Date.valueOf(o.getGeldigTot()));
            pstmt.setInt(2, o.getKlasse());
            pstmt.setDouble(3, o.getSaldo());
            pstmt.setInt(4, o.getReiziger().getId());
            pstmt.setInt(5, o.getKaartNummer());
            pstmt.executeUpdate();
            pstmt.close();
            return o;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int kaartNummer) {
        boolean result = false;
        String query = "delete from ov_chipkaart where kaart_nummer = ?";
        try (Connection conn = super.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, kaartNummer);
            result = (pstmt.executeUpdate() == 1);
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
