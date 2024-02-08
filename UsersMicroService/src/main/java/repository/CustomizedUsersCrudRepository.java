package repository;

import entity.eUsers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomizedUsersCrudRepository extends CrudRepository<eUsers, Integer>, CustomizedUsersRepository {

    eUsers findByLogin(String login);

}
