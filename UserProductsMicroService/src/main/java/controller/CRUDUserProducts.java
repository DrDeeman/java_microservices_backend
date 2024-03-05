package controller;

import DAO.DAOUserProducts;
import entity.eProducts;
import entity.eUsers;
import exception.EntityFieldException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class CRUDUserProducts {


    @Autowired
    DAOUserProducts dao;

    @GetMapping(value="/jdbc/user/{id}/products")
    public eUsers getUserWithProducts(@PathVariable("id") Optional<eUsers> tuser) throws Exception {
        return tuser.orElseThrow(()->new Exception("user not found"));
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
