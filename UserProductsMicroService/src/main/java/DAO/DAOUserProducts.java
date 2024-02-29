package DAO;

import entity.eProducts;
import entity.eUsers;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CustomizedUserProductsCrudRepository;

@Service
public class DAOUserProducts {


    @Autowired
    CustomizedUserProductsCrudRepository rep;

    @Autowired
    SessionFactory factory;




    //@Transactional
    public void addedProductForUser(@NotNull eUsers user, @NotNull eProducts product){
        user.addProduct(product);
        rep.save(user);
      //  factory.getCurrentSession().persist(user);
    }

}
