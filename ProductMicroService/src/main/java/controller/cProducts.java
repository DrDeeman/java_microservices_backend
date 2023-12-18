package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import DAO.DAOProducts;
import entity.eProducts;
import java.util.List;

@RestController
@RequestMapping(value="/products")
public class cProducts {

    @Autowired
    private DAOProducts DAOP;

  @GetMapping(value="/")
  public List<eProducts> getProducts(){
      return this.DAOP.getProducts();
  }
}
