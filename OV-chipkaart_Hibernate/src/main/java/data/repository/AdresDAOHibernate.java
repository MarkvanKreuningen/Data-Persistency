package data.repository;

import data.HibernateBaseDao;
import data.entity.Adres;
import data.entity.Reiziger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AdresDAOHibernate extends HibernateBaseDao implements AdresDAO {

    @Override
    public List<Adres> findAll() {
        try (Session session = super.getCurrentSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Adres> criteria = builder.createQuery(Adres.class);
            criteria.from(Adres.class);

            return session.createQuery(criteria).getResultList();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public Adres save(Adres adres) {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.save(adres);
            session.getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
        return adres;
    }

    @Override
    public Adres update(Adres adres) {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.update(adres);
            session.getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
        return adres;
    }

    @Override
    public boolean delete(int id) throws Exception {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.delete(findById(id));
            session.getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
        return true;
    }

    @Override
    public Adres findById(int id) throws Exception {
        Adres adres;
        try (Session session = super.getCurrentSession()) {
            adres = session.load(Adres.class, id);
        } catch (Exception e) {
            throw new Exception(e.toString());
        } finally {
            closeCurrentSession();
        }
        return adres;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        return null;
    }
}
