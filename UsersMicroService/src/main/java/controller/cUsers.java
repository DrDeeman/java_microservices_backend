package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import DAO.DAOUsers;
import entity.eUsers;
import exception.EntityException;
import java.util.List;

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
    headers.add("Content-type","application/text");
    return new ResponseEntity<>("User created",headers,HttpStatus.OK);
   }
}
