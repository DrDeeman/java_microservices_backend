package DAO;


import exception.UserNotFoundException;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import entity.eUsers;
import java.util.List;

@Repository
public class DAOUsers {


    @Autowired
    SessionFactory factory;

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


    public void addUser(eUsers user){
        Session session = this.factory.openSession();
        Transaction tr = session.beginTransaction();
        session.persist(user);
        tr.commit();
        session.close();
    }
}
