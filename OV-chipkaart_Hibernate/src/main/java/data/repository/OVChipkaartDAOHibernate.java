package data.repository;

import data.HibernateBaseDao;
import data.entity.OVChipkaart;
import data.entity.Product;
import data.entity.Reiziger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.List;

public class OVChipkaartDAOHibernate extends HibernateBaseDao implements OVChipkaartDAO {

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
//        TODO: reiziger toevoegen
        try (Session session = super.getCurrentSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<OVChipkaart> criteria = builder.createQuery(OVChipkaart.class);
            criteria.from(OVChipkaart.class);

            return session.createQuery(criteria).getResultList();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public OVChipkaart findById(int kaartNumber) throws SQLException {
        OVChipkaart ovChipkaart;
        try (Session session = super.getCurrentSession()) {
            ovChipkaart = session.get(OVChipkaart.class, kaartNumber);
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return ovChipkaart;
    }

    @Override
    public OVChipkaart save(OVChipkaart ovChipkaart) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.save(ovChipkaart);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return ovChipkaart;
    }

    @Override
    public OVChipkaart update(OVChipkaart ovChipkaart) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.update(ovChipkaart);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return ovChipkaart;
    }

    @Override
    public boolean delete(int kaartNummer) throws SQLException {
        try (Session session = super.getCurrentSession()) {
            session.beginTransaction();
            session.delete(findById(kaartNummer));
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException(e.toString());
        } finally {
            closeCurrentSession();
        }
        return true;
    }
}
