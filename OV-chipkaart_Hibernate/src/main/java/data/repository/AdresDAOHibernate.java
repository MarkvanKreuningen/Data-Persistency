package data.repository;

import data.HibernateBaseDao;
import data.entity.Adres;
import data.entity.Reiziger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.List;

public class AdresDAOHibernate extends HibernateBaseDao implements AdresDAO {

    @Override
    public List<Adres> findAll() throws SQLException {
        try (Session session = super.getCurrentSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Adres> criteria = builder.createQuery(Adres.class);
            criteria.from(Adres.class);

            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public Adres save(Adres adres) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.save(adres);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return adres;
    }

    @Override
    public Adres update(Adres adres) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.update(adres);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return adres;
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
    public Adres findById(int id) throws SQLException {
        Adres adres;
        try (Session session = super.getCurrentSession()) {
            adres = session.get(Adres.class, id);
        } catch (Exception e) {
            throw new SQLException(e.toString());
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
