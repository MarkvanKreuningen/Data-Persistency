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
    public List<Reiziger> findAll() throws SQLException {
        try (Session session = super.getCurrentSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Reiziger> criteria = builder.createQuery(Reiziger.class);
            criteria.from(Reiziger.class);

            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Reiziger reiziger;
        try (Session session = super.getCurrentSession()){
            reiziger = session.get(Reiziger.class, id);
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return reiziger;
    }

    @Override
    public Reiziger save(Reiziger reiziger) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.save(reiziger);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return reiziger;
    }

    @Override
    public Reiziger update(Reiziger reiziger) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.update(reiziger);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return reiziger;
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
}
