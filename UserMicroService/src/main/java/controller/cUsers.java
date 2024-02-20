package controller;

import DAO.DAOUsers;
import com.fasterxml.jackson.core.JsonProcessingException;
import entity.eUsers;
import exception.EntityException;
import exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import records.DataProfile;

import java.util.Map;

@RestController
@RequestMapping(value="/users")
public class cUsers {


    @Autowired
    DAOUsers dUsers;



@PostMapping(value="/")
public ResponseEntity<String> createUser(@Valid @RequestBody eUsers user, BindingResult valid_result)
        throws JsonProcessingException, EntityException{
    if(valid_result.hasErrors())
         throw new EntityException(valid_result.getAllErrors());
    this.dUsers.addUser(user);

    MultiValueMap<String, String> headers = new HttpHeaders();
    headers.add("Content-type","text/plain");
    return new ResponseEntity<>("User created",headers,HttpStatus.OK);
   }


    @PatchMapping(value="/")
    public eUsers editProfile(@RequestBody DataProfile data, @RequestHeader Map<String,String> headers)throws UserNotFoundException{
       Integer user_id = Integer.parseInt(headers.get("user_id"));
          if(user_id==null) throw new UserNotFoundException();
           eUsers user = this.dUsers.getUser(user_id);
           this.dUsers.editUser(user, data);
           return user;
    }


}
