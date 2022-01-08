package P5;

import P5.data.entity.Adres;
import P5.data.entity.OVChipkaart;
import P5.data.entity.Product;
import P5.data.entity.Reiziger;
import P5.data.repository.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ReizigerDao reizigerDao = new ReizigerDaoPsql();
        AdresDAO adresDao = new AdresDAOPsql();
        OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOPsql();
        testOvChipkaartDao(reizigerDao, adresDao, ovChipkaartDAO);
        testProductAndOvChipkaart();
    }


    /**
     * P4. OvChipkaartDao: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Adres DAO
     *
     * @throws SQLException
     */
    private static void testOvChipkaartDao(ReizigerDao reizigerDAO, AdresDAO adresDAO, OVChipkaartDAO ovChipkaartDAO) throws SQLException {
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
        int reizigerId = 999;
        Reiziger sietske = new Reiziger(reizigerId, "S", "", "Boers", LocalDate.parse(gbdatum));
        reizigerDAO.save(sietske);


        // Maak een nieuw adres aan en persisteer deze in de database
        int adresId = 77;
        Adres adres = new Adres(adresId, "3432LX", "3", "Wulpenlaan", "Wulpenstad", sietske.getId());
        System.out.print("[Test] Eerst " + adresList.size() + " adressen, na AdresDAO.save() ");
        adresDAO.save(adres);
        adresList = adresDAO.findAll();
        System.out.println(adresList.size() + " adressen\n");

        // Een adres vinden op id
        Adres adres77 = adresDAO.findById(adresId);
        System.out.println("[Test] " + adres77.toString() + " gevonden dmv id met id " + adresId + "\n");

        // Update een adres
        System.out.println("[Test] Eerst was de postcode "+adres77.getPostcode()+", na AdresDAO.update(adres) is het ");
        adres77.setPostcode("3641WJ");
        adresDAO.update(adres77);
        System.out.println(adres77.getPostcode() + "\n");

        // Alle reizigers met 1 op 1 relatie
        System.out.println("[Test] Reiziger.findAll() geeft de volgende reizigers:");
        List<Reiziger> reizigers = reizigerDAO.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();


        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Een reiziger vinden op id
        Reiziger reiziger6 = reizigerDAO.findById(reizigerId);
        System.out.println("[Test] " + reiziger6.toString() + " gevonden dmv id met id " + reizigerId + "\n");

        // Update een reiziger
        System.out.println("[Test] Eerst was de achternaam "+reiziger6.getAchternaam()+", na ReizigerDao.update(reiziger) is het ");
        reiziger6.setAchternaam("van Kreuningen");
        reizigerDAO.update(reiziger6);
        System.out.println(reiziger6.getAchternaam() + "\n");

        // Delete een adres
        System.out.print("[Test] Eerst " + adresList.size() + " adressen, na AdresDao.delete(" + adresId + ") ");
        adresDAO.delete(adresId);
        adresList = adresDAO.findAll();
        System.out.println(adresList.size() + " adressen\n");

        // Delete een reiziger
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete(" + reizigerId + ") ");
        reizigerDAO.delete(reizigerId);
        reizigers = reizigerDAO.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }

    private static void testProductAndOvChipkaart() throws SQLException {
        AdresDAO adresDAO = new AdresDAOPsql();
        OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOPsql();
        ProductDao productDAO = new ProductDAOPsql();
        ReizigerDao reizigerDAO = new ReizigerDaoPsql();

        Reiziger mark = new Reiziger(111, "M", "van", "Kreuningen", LocalDate.parse("1998-09-12"));
        reizigerDAO.save(mark);
        Adres adresMark = new Adres(222, "3641WJ", "46A", "Houtduif", "Mijdrecht", mark.getId());
        adresDAO.save(adresMark);

        Product product = new Product(555, "Marks", "Marks totaal eigen product", 2.50);
        productDAO.save(product);
        OVChipkaart ovChipkaart = new OVChipkaart(9999, LocalDate.parse("2025-03-14"), 1, 1500.00, mark);
        ovChipkaartDAO.save(ovChipkaart);

        //read
        OVChipkaart readOvChipkaart = ovChipkaartDAO.findById(9999);
        System.out.println("Ovchipkaart: "+readOvChipkaart);

        //update
        System.out.println("\nEerst was de klasse van de ovchipkaart: " + readOvChipkaart.getKlasse());
        readOvChipkaart.setKlasse(2);
        ovChipkaartDAO.update(readOvChipkaart);
        OVChipkaart updateOvChipkaart = ovChipkaartDAO.findById(9999);
        System.out.println("\nNa update is de klasse: " + updateOvChipkaart.getKlasse());

        //Add product to ovchipkaart
        System.out.println("\nReiziger voordat product is toegevoegd aan ovchipkaart: \n" + reizigerDAO.findById(111));
        ovChipkaartDAO.addProductToOvchipKaart(updateOvChipkaart, 555);
        System.out.println("Reiziger nadat de product is toegevoegd aan de ovchipkaart: \n" + reizigerDAO.findById(111));

        // Alle delete statements
        ovChipkaartDAO.delete(9999);
        productDAO.delete(555);
        adresDAO.delete(222);
        reizigerDAO.delete(111);
    }

}
