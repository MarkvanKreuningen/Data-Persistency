import data.entity.Adres;
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

    public static void main(String[] args) throws SQLException {
//        testFetchAll();
        testDAOHibernate();
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
    private static void testDAOHibernate() {
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
        int reizigerId = 77;
        Reiziger sietske = new Reiziger(reizigerId, "S", "", "Boers", LocalDate.parse(gbdatum));
        reizigerDAO.save(sietske);


        // Maak een nieuw adres aan en persisteer deze in de database
        int id = 999;
        Adres adres = new Adres(id, "3432LX", "3", "Wulpenlaan", "Wulpenstad", sietske);
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
        List<Reiziger> reizigers = reizigerDAO.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Delete een adres
        System.out.print("[Test] Eerst " + adresList.size() + " adressen, na AdresDao.delete(" + id + ") ");
        adresDAO.delete(id);
        adresList = adresDAO.findAll();
        System.out.println(adresList.size() + " adressen\n");
        reizigerDAO.delete(reizigerId);
    }
}
