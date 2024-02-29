package repository;

import entity.eUsers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomizedUserProductsCrudRepository extends CrudRepository<eUsers, Integer> {

}
