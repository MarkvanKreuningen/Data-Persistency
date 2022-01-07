package data.repository;

import data.HibernateBaseDao;
import data.entity.Reiziger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
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
    public Reiziger findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        return null;
    }

    @Override
    public Reiziger save(Reiziger reiziger) {
        return null;
    }

    @Override
    public Reiziger update(Reiziger reiziger) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
