package repository;

import entity.eUsers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomizedUsersCrudRepository extends ListCrudRepository<eUsers, Integer>, CustomizedUsersRepository {

    eUsers findByLogin(String login);

}
