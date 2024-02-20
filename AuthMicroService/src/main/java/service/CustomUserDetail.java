package service;

import entity.eUsers;
import exception.UserNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import repository.CustomizedUsersCrudRepository;

@Service
public class CustomUserDetail implements UserDetailsService {

    @Autowired
    private SessionFactory factory;

    @Autowired
    private CustomizedUsersCrudRepository rep;
    @Override
    public UserDetails loadUserByUsername(String login) throws  UserNotFoundException {

        eUsers result = this.rep.findByLogin(login);
        if(result ==null)  throw new InternalAuthenticationServiceException("User for login:"+login+" not found");
        return result;

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
