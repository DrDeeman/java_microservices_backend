package controller;

import DAO.DAOUsers;
import entity.eUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import records.LoginRequest;
import records.TestCustomRecord;

import java.util.List;

@RestController
@RequestMapping(value="/test")
public class test {

    @Autowired
    DAOUsers dao;

    @PostMapping(value="/getUserByLoginAndPassword")
    public eUsers getUser(@RequestBody LoginRequest data){
        return dao.getUser(data.login());
    }

    @GetMapping(value="/getUserByEmailGroup")
    public List<TestCustomRecord> getListUserByEmailGroup(){
        return dao.getUserByGroupEmail();
    }
}
