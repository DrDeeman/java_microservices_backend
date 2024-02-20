package DAO;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.eUsers;
import exception.EntityException;
import exception.UserNotFoundException;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.SmartValidator;
import records.DataProfile;
import records.TestCustomRecord;
import repository.CustomizedUsersCrudRepository;
import service.ApplicationContextProvider;
import service.KafkaProducer;

import java.util.List;


@Repository
public class DAOUsers {


    @Autowired
    SessionFactory factory;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    KafkaProducer kp;



    @Autowired
    CustomizedUsersCrudRepository rep;






    public eUsers getUser(String login) {
        return rep.getCustomUserByLogin(login);
    }

    public List<TestCustomRecord> getUserByGroupEmail(){
        return rep.getListGroupEmail();
    }


    public eUsers getUser(Integer id) {
        if(id==null) throw new UserNotFoundException();
        try(Session session = this.factory.openSession()) {
            Query<eUsers> q = session.createQuery("from eUsers where id=:idu", eUsers.class);
            q.setParameter("idu", id);
            eUsers user = q.getSingleResult();
            session.close();
            return user;
        }catch(NoResultException ex){
            throw new UserNotFoundException();
        }
    }


    public void addUser(eUsers user) throws JsonProcessingException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String plainPassword = user.getPassword();
        user.setPassword(encoder.encode(plainPassword));

        Session session = this.factory.openSession();
        Transaction tr = session.beginTransaction();
        session.persist(user);
        tr.commit();
        session.close();



        ObjectNode u = this.mapper.createObjectNode();
        u.put("login",user.getLogin());
        u.put("password",plainPassword);
        u.put("email",user.getEmail());


        kp.sendMessage( this.mapper.writeValueAsString(u),"messages");
    }


    public void editUser(eUsers user, DataProfile data){
          System.out.println("start edit");
        user.setEmail(data.email());
          SmartValidator validator = ApplicationContextProvider.getApplicationContext().getBean(SmartValidator.class);
          DataBinder binder = new DataBinder(user);
          binder.setValidator(validator);
          binder.validate();
          BindingResult valid_result = binder.getBindingResult();
          if(valid_result.hasErrors())
              throw new EntityException(valid_result.getAllErrors());

          Session session = this.factory.openSession();
          Transaction tr = session.beginTransaction();
          session.merge(user);
          tr.commit();
          session.close();

    }
}
