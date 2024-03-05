package entity;
// Generated 28 февр. 2024 г., 12:39:46 by Hibernate Tools 3.2.2.GA


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * eUsers generated by hbm2java
 */
@Entity //for JPA, no use R2dbc
@Table(name="users")
public class eUsers  implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="generator")
    @SequenceGenerator(name="generator", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name="id", nullable=false)
     private int id;

    @Column(name="name")
     private String name;

    @Column(name="login")
     private String login;

    @Column(name="password")
     private String password;

   @Column(name="email")
     private String email;

    @OneToMany(mappedBy="user", fetch= FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<eProducts> products;

    public eUsers() {
    }

    public eUsers(String name, String login, String password, String email) {
       this.name = name;
       this.login = login;
       this.password = password;
       this.email = email;
       this.products = new ArrayList<>();
    }

    public void addProduct(eProducts product){
        product.setUser(this);
        this.products.add(product);
    }

    public void setProducts(List<eProducts> products){
        this.products = products;
    }

    public List<eProducts> getProducts(){
        return this.products;
    }
    

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    

    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("id").append("='").append(getId()).append("' ");			
      buffer.append("name").append("='").append(getName()).append("' ");			
      buffer.append("login").append("='").append(getLogin()).append("' ");			
      buffer.append("password").append("='").append(getPassword()).append("' ");			
      buffer.append("email").append("='").append(getEmail()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }

   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof eUsers) ) return false;
		 eUsers castOther = ( eUsers ) other; 
         
		 return (this.getId()==castOther.getId())
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getLogin()==castOther.getLogin()) || ( this.getLogin()!=null && castOther.getLogin()!=null && this.getLogin().equals(castOther.getLogin()) ) )
 && ( (this.getPassword()==castOther.getPassword()) || ( this.getPassword()!=null && castOther.getPassword()!=null && this.getPassword().equals(castOther.getPassword()) ) )
 && ( (this.getEmail()==castOther.getEmail()) || ( this.getEmail()!=null && castOther.getEmail()!=null && this.getEmail().equals(castOther.getEmail()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getId();
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getLogin() == null ? 0 : this.getLogin().hashCode() );
         result = 37 * result + ( getPassword() == null ? 0 : this.getPassword().hashCode() );
         result = 37 * result + ( getEmail() == null ? 0 : this.getEmail().hashCode() );
         return result;
   }   


}


