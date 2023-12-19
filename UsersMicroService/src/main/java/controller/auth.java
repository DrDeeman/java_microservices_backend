package controller;

import DAO.DAOUsers;
import entity.eUsers;
import exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class auth {

    @Autowired
    DAOUsers dUsers;

    @GetMapping(value="/auth")
    public eUsers getAuth(HttpSession session)throws UserNotFoundException{
        Integer id_user = (Integer)session.getAttribute("user");
        eUsers user = this.dUsers.getUser(id_user);
        return user;
    }

    @GetMapping(value="/logout")
    public void logout(HttpSession session){
        session.setAttribute("user",null);
    }


    @PostMapping(value="/login")
    public eUsers login(HttpServletRequest request, HttpSession session)
    throws UserNotFoundException{
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        eUsers user = this.dUsers.getUser(login,password);
        session.setAttribute("user", user.getId());
        return user;
        }

    }

