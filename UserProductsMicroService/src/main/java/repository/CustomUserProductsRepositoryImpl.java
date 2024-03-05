package repository;

import entity.eUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.UserMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.ResponseEntity.notFound;



public class CustomUserProductsRepositoryImpl implements CustomUserProductsRepository{

    @Autowired
    DatabaseClient dbh = null;

    @FunctionalInterface
    private interface ParametizedSqlSupplier{

        Flux<eUsers> getSqlStream(String str, Optional<Map<String,Object>> params);
    }

    ParametizedSqlSupplier supplier = (str, params)->{
        DatabaseClient.GenericExecuteSpec sql = dbh.sql(str);
        Map<String, Object> map = params.orElse(new HashMap<>());
        for(String k : map.keySet())
            sql = sql.bind(k,map.get(k));

        return sql.map(mapper::apply)
                .all()
                .collect(Collectors.groupingBy(eUsers::getId,Collectors.reducing(null,joiner)))
                .flatMapIterable(el-> el.values());

    };

    @Override
    public Flux<eUsers> searchByEmail(String email){
       Map<String,Object> params = new HashMap<>();
       params.put("m",email);
      return supplier.getSqlStream("SELECT users.id AS user_id, users.name AS user_name, users.login AS user_login, users.password AS user_password, users.email AS user_email," +
              "products.* FROM users JOIN products ON users.id = products.id_user WHERE email = :m",Optional.of(params));

    }

    public Flux<eUsers> customFindAll(){
        return supplier.getSqlStream("SELECT users.id AS user_id, users.name AS user_name, users.login AS user_login, users.password AS user_password, users.email AS user_email," +
                "products.* FROM users JOIN products ON users.id = products.id_user",Optional.empty());
    }

    public Mono<eUsers> searchById(Integer id){
        Map<String,Object> params = new HashMap<>();
        params.put("idu",id);
        return supplier.getSqlStream("SELECT users.id AS user_id, users.name AS user_name, users.login AS user_login, users.password AS user_password, users.email AS user_email," +
                "products.* FROM users JOIN products ON users.id = products.id_user WHERE users.id =:idu",Optional.of(params))
                .elementAt(0).onErrorResume((e)-> {
                    System.out.println(e.getMessage());
                    return Mono.error(new Exception("not found"));
                });


    }





}
