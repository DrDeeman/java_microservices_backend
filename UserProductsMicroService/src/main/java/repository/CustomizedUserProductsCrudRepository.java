package repository;

import entity.eUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface CustomizedUserProductsCrudRepository extends R2dbcRepository<eUsers, Integer> {

    @Autowired
    DatabaseClient dbh = null;

    @Query(value ="SELECT * FROM users WHERE email=:m")
    public Flux<eUsers> findAllByEmail( @Param("m") String mail); //old method creating query



    //public Flux<eUsers> searchByEmail(String email);

    public Flux<eUsers> searchByEmail(String email){
          dbh.sql("SELECT users.id ")
    }


}
