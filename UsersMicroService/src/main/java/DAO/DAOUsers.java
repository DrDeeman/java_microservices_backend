package DAO;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import entity.eUsers;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.SmartValidator;
import records.DataProfile;
import service.ApplicationContextProvider;
import service.KafkaProducer;


@Repository
public class DAOUsers {


    @Autowired
    SessionFactory factory;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    KafkaProducer kp;

    @Autowired
    BCryptPasswordEncoder encoder;




    public eUsers getUser(String login, String password) throws NoResultException {
        try(Session session = this.factory.openSession()) {
            Query<eUsers> q = session.createQuery("from eUsers where login=:l and password=:p", eUsers.class);
            q.setParameter("l", login);
            q.setParameter("p", password);
            eUsers user = q.getSingleResult();
            return user;
        }catch(NoResultException ex){
            throw new UserNotFoundException(login,password);
        }
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

        String plainPassword = user.getPassword();
        user.setPassword(this.encoder.encode(plainPassword));

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
