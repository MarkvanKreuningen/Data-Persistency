package P2;

import P2.data.entity.Reiziger;
import P2.data.repository.ReizigerDao;
import P2.data.repository.ReizigerDaoPsql;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ReizigerDao reizigerDao = new ReizigerDaoPsql();
        testReizigerDAO(reizigerDao);
    }



    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDao rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        int id = 77;
        Reiziger sietske = new Reiziger(id, "S", "", "Boers", LocalDate.parse(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Een reiziger vinden op id
        Reiziger reiziger77 = rdao.findById(id);
        System.out.println("[Test] " + reiziger77.toString() + " gevonden dmv id met id " + id + "\n");

        // Update een reiziger
        System.out.println("[Test] Eerst was de achternaam "+reiziger77.getAchternaam()+", na ReizigerDao.update(reiziger) is het ");
        reiziger77.setAchternaam("van Kreuningen");
        rdao.update(reiziger77);
        System.out.println(reiziger77.getAchternaam() + "\n");

        // Delete een reiziger
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete(" + id + ") ");
        rdao.delete(id);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }
}
