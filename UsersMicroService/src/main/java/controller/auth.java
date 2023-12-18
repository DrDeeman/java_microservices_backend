package controller;

import DAO.DAOUsers;
import entity.eUsers;
import exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class auth {

    @Autowired
    DAOUsers dUsers;

    @GetMapping(value="/auth")
    public eUsers getAuth(HttpSession session)throws UserNotFoundException{
        Integer id_user = (Integer)session.getAttribute("user");
        eUsers user = this.dUsers.getUser(id_user);
        System.out.println(user);
        if(user == null)throw new UserNotFoundException();
        return user;
    }


    @PostMapping(value="/login")
    public eUsers login(HttpServletRequest request, HttpSession session)
    throws UserNotFoundException{
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        eUsers user = this.dUsers.getUser(login,password);
        if(user!=null) {
            session.setAttribute("user", user.getId());
            return user;
        }else throw new UserNotFoundException(login,password);

        }

    }

