package repository;

import entity.eUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface eUsersRepository extends JpaRepository<eUsers, Integer> {

}
