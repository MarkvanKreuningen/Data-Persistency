package data.repository;

import data.HibernateBaseDao;
import data.entity.Product;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.List;

public class ProductDAOHibernate extends HibernateBaseDao implements ProductDAO {

    @Override
    public Product save(Product product) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return product;
    }

    @Override
    public Product update(Product product) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return product;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.delete(findById(id));
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return true;
    }

    @Override
    public Product findById(int id) throws SQLException {
        Product product;
        try (Session session = super.getCurrentSession()) {
            product = session.get(Product.class, id);
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return product;
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
