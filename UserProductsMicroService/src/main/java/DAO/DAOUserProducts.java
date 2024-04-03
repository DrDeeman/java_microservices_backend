package DAO;

import CustomAnnotation.TestAnnotation;
import CustomAnnotation.TestMethodAnnotation;
import entity.eProducts;
import entity.eUsers;
import exception.EntityFieldException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import repository.CustomizedUserProductsCrudRepository;

@Validated
@Service
public class DAOUserProducts {


    @Autowired
    CustomizedUserProductsCrudRepository rep;

    @Autowired
    SessionFactory factory;


    public Mono<eUsers> getUser(Integer id){
       return  rep.searchById(id);
    }

    @TestMethodAnnotation
    public eUsers forTestValidationMethodAnnotation(eUsers user,  eUsers user2){
        return user;
    }


    //@Transactional
    public void addedProductForUser(@NotNull eUsers user, @NotNull eProducts product){
        user.addProduct(product);
        rep.save(user);
      //  factory.getCurrentSession().persist(user);
    }

    public Flux<eUsers> getUsersByEmail(String mail){
        return rep.searchByEmail(mail);
    }


    public Flux<eUsers> getUsers(){
        return rep.customFindAll();
    }

}
