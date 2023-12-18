package DAO;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import entity.eProducts;
import java.util.List;

@Repository
public class DAOProducts {


    @Autowired
    SessionFactory factory;

    public List<eProducts> getProducts(){
        Session sess = factory.openSession();
        List<eProducts> rows = sess.createQuery("from eProducts", eProducts.class).getResultList();
        sess.close();
        return rows;
    }
}
