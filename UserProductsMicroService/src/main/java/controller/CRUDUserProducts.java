package controller;

import CustomAnnotation.TestMethodAnnotation;
import CustomAnnotation.Validators.TestAnnotationValidator;
import DAO.DAOUserProducts;
import entity.eProducts;
import entity.eUsers;
import exception.EntityFieldException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.executable.ValidateOnExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.ApplicationContextProvider;

import java.util.Optional;
import java.util.Set;

@Validated
@RestController
public class CRUDUserProducts {


    @Autowired
    DAOUserProducts dao;

    @Autowired
    Validator validator;


    @GetMapping(value="/jdbc/user/{id}/products")
    public eUsers getUserWithProducts(@PathVariable("id") Optional<eUsers> tuser) throws Exception {
        eUsers user = tuser.orElseThrow(()->new Exception("user not found"));
        user.setLogin(null);
      //  Set<ConstraintViolation<eUsers>> viol = validator.validate(user);
        //if(!viol.isEmpty())
       //      throw new EntityFieldException(viol);
        this.dao.forTestValidationMethodAnnotation(user,user);
        /*
        SmartValidator validator = ApplicationContextProvider.getApplicationContext().getBean(SmartValidator.class);
        DataBinder binder = new DataBinder(user);

        binder.setValidator(validator);
        binder.addValidators(new TestAnnotationValidator());
        binder.validate();
        BindingResult valid_result = binder.getBindingResult();

         */


        return user;
    }

    @GetMapping(value="/r2dbc/user/{id}/products")
    public Mono<eUsers> getUserWithProducts(@PathVariable("id") Integer id)throws Exception{
       return dao.getUser(id);
    }

    @PostMapping(value="/user/{id}/products")
    public Flux<eUsers> addedProductUser(
             @PathVariable("id") Optional<eUsers> tuser,
             @Valid @RequestBody eProducts product,
             BindingResult result
    ) throws Exception {
        eUsers user = tuser.orElseThrow(()->new Exception("user not found"));
        if(result.hasErrors()) throw new EntityFieldException(result.getAllErrors());
        dao.addedProductForUser(user, product);
        return this.dao.getUsers();
    }

    @GetMapping(value="/users")
    public Flux<eUsers> getUsersByEmail(){
        return this.dao.getUsersByEmail("stalkerdrdeeman2@gmail.com");
    }


}
