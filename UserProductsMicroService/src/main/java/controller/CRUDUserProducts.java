package controller;

import DAO.DAOUserProducts;
import entity.eProducts;
import entity.eUsers;
import exception.EntityFieldException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping(value="/user/{id}")
public class CRUDUserProducts {


    @Autowired
    DAOUserProducts dao;

    @GetMapping(value="/products")
    public Mono<eUsers> getUserWithProducts(@PathVariable("id") Optional<eUsers> tuser) throws Exception {
        return Mono.just(tuser.orElseThrow(()->new Exception("user not found")));
    }

    @PostMapping(value="/products")
    public Mono<eUsers> addedProductUser(
             @PathVariable("id") Optional<eUsers> tuser,
             @Valid @RequestBody eProducts product,
             BindingResult result
    ) throws Exception {
        eUsers user = tuser.orElseThrow(()->new Exception("user not found"));
        if(result.hasErrors()) throw new EntityFieldException(result.getAllErrors());
        dao.addedProductForUser(user, product);
        return Mono.just(user);
    }
}
