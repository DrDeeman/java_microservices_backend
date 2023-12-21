package service;

import DAO.DAOUsers;
import entity.eUsers;
import exception.UserNotFoundException;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetail implements UserDetailsService {

    @Autowired
    private SessionFactory factory;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try(Session session = this.factory.openSession()) {
            Query<eUsers> q = session.createQuery("from eUsers where login = :l", eUsers.class);
            q.setParameter("l", login);
            return q.getSingleResult();
        }catch(NoResultException ex){
            throw new UsernameNotFoundException("User for login:"+login+" not found");
        }
    }
}
