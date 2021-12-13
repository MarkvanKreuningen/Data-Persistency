package P4;

import P4.data.entity.Reiziger;
import P4.data.repository.AdresDAO;
import P4.data.repository.AdresDAOPsql;
import P4.data.repository.ReizigerDao;
import P4.data.repository.ReizigerDaoPsql;
import P4.data.repository.OVChipkaartDAO;
import P4.data.repository.OVChipkaartDAOPsql;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ReizigerDao reizigerDao = new ReizigerDaoPsql();
        AdresDAO adresDao = new AdresDAOPsql();
        OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOPsql();
        testOvChipkaartDao(reizigerDao, adresDao, ovChipkaartDAO);
    }


    /**
     * P4. OvChipkaartDao: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Adres DAO
     *
     * @throws SQLException
     */
    private static void testOvChipkaartDao(ReizigerDao reizigerDao, AdresDAO adresDAO, OVChipkaartDAO ovChipkaartDAO) throws SQLException {
        // Alle reizigers met 1 op veel relatie
        System.out.println("[Test] Reiziger.findAll() geeft de volgende reizigers:");
        List<Reiziger> reizigers = reizigerDao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();
    }

}
