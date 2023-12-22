package service;

import DAO.DAOUsers;
import entity.eUsers;
import exception.UserNotFoundException;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.eUsersRepository;

import java.util.NoSuchElementException;

@Service
public class CustomUserDetail implements UserDetailsService {

    @Autowired
    private SessionFactory factory;

    @Autowired
    private eUsersRepository rep;
    @Override
    public UserDetails loadUserByUsername(String login) throws  UsernameNotFoundException {
        try {
        eUsers u = new eUsers();
        u.setLogin(login);
        return this.rep.findOne(Example.of(u)).get();
        }catch(NoSuchElementException ex){
            throw new UsernameNotFoundException("User for login:"+login+" not found");
        }
                /*
        try(Session session = this.factory.openSession()) {
            Query<eUsers> q = session.createQuery("from eUsers where login = :l", eUsers.class);
            q.setParameter("l", login);
            return q.getSingleResult();
        }catch(NoResultException ex){
            throw new UsernameNotFoundException("User for login:"+login+" not found");
        }
        */

    }
}
