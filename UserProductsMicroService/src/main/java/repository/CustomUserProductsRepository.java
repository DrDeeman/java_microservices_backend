package repository;

import entity.eUsers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.UserMapper;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public interface CustomUserProductsRepository {

    static UserMapper mapper = new UserMapper();
    static BinaryOperator<eUsers> joiner = (f,s)-> Optional.ofNullable(f)
            .map(c-> {
                c.setProducts(Stream.concat(c.getProducts().stream(),s.getProducts().stream()).toList());
                return c;
            })
            .orElseGet(()->s);





    public Flux<eUsers> searchByEmail(String email);
    public Mono<eUsers> searchById(Integer id);
    public Flux<eUsers> customFindAll();


}
