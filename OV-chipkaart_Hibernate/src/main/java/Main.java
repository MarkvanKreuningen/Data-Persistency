import data.entity.Adres;
import data.entity.OVChipkaart;
import data.entity.Product;
import data.entity.Reiziger;
import data.repository.*;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import javax.persistence.metamodel.EntityType;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws Exception {
        testFetchAll();
        testDAOHibernate();
        testProductAndOvChipkaart();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }


    /**
     * P7. testDAOHibernate.
     */
    private static void testDAOHibernate() throws SQLException {
        AdresDAO adresDAO = new AdresDAOHibernate();
        OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOHibernate();
        ProductDAO productDAO = new ProductDAOHibernate();
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate();
        System.out.println(productDAO.findAll());
        System.out.println(ovChipkaartDAO.findByReiziger(null));
        System.out.println(reizigerDAO.findAll());

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
        Adres adres = new Adres(adresId, "3432LX", "3", "Wulpenlaan", "Wulpenstad", sietske);
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
        System.out.println("\n---------- Test Product/OVChipkaart -DAO -------------");

        AdresDAO adresDAO = new AdresDAOHibernate();
        OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOHibernate();
        ProductDAO productDAO = new ProductDAOHibernate();
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate();

        Reiziger mark = new Reiziger(111, "M", "van", "Kreuningen", LocalDate.parse("1998-09-12"));
        reizigerDAO.save(mark);
        Adres adresMark = new Adres(222, "3641WJ", "46A", "Houtduif", "Mijdrecht", mark);
        adresDAO.save(adresMark);

        Product product = new Product(555, "Marks", "Marks totaal eigen product", 2.50);
        productDAO.save(product);
        OVChipkaart ovChipkaart = new OVChipkaart(9999, LocalDate.parse("2025-03-14"), 1, 1500.00, mark);
        ovChipkaartDAO.save(ovChipkaart);

        System.out.println(ovChipkaartDAO.findById(9999));
        List<Product> products = new ArrayList<>();
        products.add(product);
        ovChipkaart.setProducts(products);
        ovChipkaartDAO.update(ovChipkaart);
        System.out.println(reizigerDAO.findById(111));

        ovChipkaartDAO.delete(9999);
        productDAO.delete(555);
        adresDAO.delete(222);
        reizigerDAO.delete(111);
    }
}
