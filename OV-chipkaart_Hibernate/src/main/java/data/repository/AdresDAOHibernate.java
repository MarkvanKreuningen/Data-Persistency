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
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Adres findById(int id) {
        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        return null;
    }
}
