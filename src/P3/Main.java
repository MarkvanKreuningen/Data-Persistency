package P3;

import P3.data.entity.Reiziger;
import P3.data.entity.Adres;
import P3.data.repository.ReizigerDao;
import P3.data.repository.ReizigerDaoPsql;
import P3.data.repository.AdresDAO;
import P3.data.repository.AdresDAOPsql;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ReizigerDao reizigerDao = new ReizigerDaoPsql();
        AdresDAO adresDao = new AdresDAOPsql();
        testAdresDao(reizigerDao, adresDao);
    }

    /**
     * P3. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Adres DAO
     *
     * @throws SQLException
     */
    private static void testAdresDao(ReizigerDao reizigerDao, AdresDAO adresDAO) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");


        // Haal alle adressen op uit de database
        List<Adres> adresList = adresDAO.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adresList) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        int reizigerId = 77;
        Reiziger sietske = new Reiziger(reizigerId, "S", "", "Boers", LocalDate.parse(gbdatum));
        reizigerDao.save(sietske);


        // Maak een nieuw adres aan en persisteer deze in de database
        int id = 999;
        Adres adres = new Adres(id, "3432LX", "3", "Wulpenlaan", "Wulpenstad", reizigerId);
        System.out.print("[Test] Eerst " + adresList.size() + " adressen, na AdresDAO.save() ");
        adresDAO.save(adres);
        adresList = adresDAO.findAll();
        System.out.println(adresList.size() + " adressen\n");

        // Een adres vinden op id
        Adres adres999 = adresDAO.findById(id);
        System.out.println("[Test] " + adres999.toString() + " gevonden dmv id met id " + id + "\n");

        // Update een adres
        System.out.println("[Test] Eerst was de postcode "+adres999.getPostcode()+", na AdresDAO.update(adres) is het ");
        adres999.setPostcode("3641WJ");
        adresDAO.update(adres999);
        System.out.println(adres999.getPostcode() + "\n");

        // Alle reizigers met 1 op 1 relatie
        System.out.println("[Test] Reiziger.findAll() geeft de volgende reizigers:");
        List<Reiziger> reizigers = reizigerDao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Delete een adres
        System.out.print("[Test] Eerst " + adresList.size() + " adressen, na AdresDao.delete(" + id + ") ");
        adresDAO.delete(id);
        adresList = adresDAO.findAll();
        System.out.println(adresList.size() + " adressen\n");
        reizigerDao.delete(reizigerId);

    }
}
