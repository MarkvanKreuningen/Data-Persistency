package data.repository;

import data.HibernateBaseDao;
import data.entity.OVChipkaart;
import data.entity.Product;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ProductDAOHibernate extends HibernateBaseDao implements ProductDAO {

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        try (Session session = super.getCurrentSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
            criteria.from(Product.class);

            return session.createQuery(criteria).getResultList();
        } finally {
            closeCurrentSession();
        }
    }
}
