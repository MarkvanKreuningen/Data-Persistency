package data.repository;

import data.HibernateBaseDao;
import data.entity.Reiziger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ReizigerDAOHibernate extends HibernateBaseDao implements ReizigerDAO {

    @Override
    public List<Reiziger> findAll() {
        try (Session session = super.getCurrentSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Reiziger> criteria = builder.createQuery(Reiziger.class);
            criteria.from(Reiziger.class);

            return session.createQuery(criteria).getResultList();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public Reiziger findById(int id) {
        Reiziger reiziger;
        try (Session session = super.getCurrentSession()){
            session.beginTransaction();
            reiziger = (Reiziger) session.load(Reiziger.class, id);
            session.getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        return null;
    }

    @Override
    public Reiziger save(Reiziger reiziger) {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.save(reiziger);
            session.getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
        return reiziger;
    }

    @Override
    public Reiziger update(Reiziger reiziger) {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.update(reiziger);
            session.getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
        return reiziger;
    }

    @Override
    public boolean delete(int id) {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.delete(findById(id));
            session.getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
        return true;
    }
}
