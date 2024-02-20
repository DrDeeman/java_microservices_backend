package repository;

import entity.eUsers;
import exception.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import records.TestCustomRecord;

import java.util.List;


public class CustomizedUsersRepositoryImpl implements CustomizedUsersRepository {


    @PersistenceContext
    EntityManager emi;

    @Autowired
    JdbcTemplate jdbc;




    @Override
    public eUsers getCustomUserByLogin(String login){
        try {
            return this.emi.createQuery("from eUsers where login=:l", eUsers.class)
                    .setParameter("l", login)
                    .getSingleResult();
        }catch(NoResultException ex){
            throw new UserNotFoundException();
        }
    }


    @Override
    public List<TestCustomRecord> getListGroupEmail(){
        return this.jdbc.query("SELECT string_agg(name,',') AS names, email FROM users GROUP BY email",(rs,rowNum)->{
           return new TestCustomRecord(rs.getString("names"),rs.getString("email"));
        });

    }


}
