package data;

import data.entity.Adres;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class HibernateBaseDao {
    private static Session currentSession;

    private static Transaction currentTransaction;

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
    }

//    private static CriteriaQuery<?> getCriteriaQuery(Object o) {
//        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
//        CriteriaQuery<?> criteria = builder.createQuery(o);
//        return criteria.from(o.class);
//    }

    public Session getCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }
}
