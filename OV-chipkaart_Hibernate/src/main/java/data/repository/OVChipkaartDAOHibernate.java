package data.repository;

import data.HibernateBaseDao;
import data.entity.OVChipkaart;
import data.entity.Product;
import data.entity.Reiziger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    public OVChipkaart findById(int kaartNumber) {
        return null;
    }

    @Override
    public OVChipkaart save(OVChipkaart ovChipkaart) {
        return null;
    }

    @Override
    public OVChipkaart update(OVChipkaart ovChipkaart) {
        return null;
    }

    @Override
    public boolean delete(int kaartNummer) {
        return false;
    }

    @Override
    public List<Integer> getKaartNummersVanProduct(Product product) {
        return null;
    }

    @Override
    public OVChipkaart addProductToOvchipKaart(OVChipkaart ovChipkaart, int productNumber) {
        return null;
    }

    @Override
    public boolean removeProductFromOvChipkaart(OVChipkaart ovChipkaart, int productNumber) {
        return false;
    }
}
