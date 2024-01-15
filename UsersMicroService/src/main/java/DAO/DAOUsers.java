package DAO;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

        user.setPassword(this.encoder.encode(user.getPassword()));

        Session session = this.factory.openSession();
        Transaction tr = session.beginTransaction();
        session.persist(user);
        tr.commit();
        session.close();


        ObjectNode dataForTopic = this.mapper.createObjectNode();
        ObjectNode u = this.mapper.createObjectNode();
        u.put("login",user.getLogin());
        u.put("password",user.getPassword());
        u.put("email",user.getEmail());
        dataForTopic.put("user",u);

        kp.sendMessage( this.mapper.writeValueAsString(dataForTopic),"messages");
    }
}
