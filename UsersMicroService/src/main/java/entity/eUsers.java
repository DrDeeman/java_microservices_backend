package entity;
// Generated 18 дек. 2023 г., 14:10:30 by Hibernate Tools 3.2.2.GA


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * eUsers generated by hbm2java
 */
@Entity
@Table(name="users")
public class eUsers  implements UserDetails, java.io.Serializable {

    @Id
    @Column(name="id", nullable=false, columnDefinition="serial")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="users_seq")
    @SequenceGenerator(name="users_seq", sequenceName="users_id_seq", allocationSize=10)
     private int id;
    @Column(name="name")
    @JsonIgnore
    private String name;
    @Column(name="login", unique = true)
    @NotBlank(message = "Name not found")
    private String login;
    @Column(name="password")
    @NotBlank(message = "Password not found")
    @Size(min = 8, message="Password too short")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(name="email")
    @NotBlank(message = "Email not found")
    @Pattern(regexp="[\\w\\d]*?@\\w*\\.(?>ru|com)", message="Email not valid")
    private String email;

    public eUsers() {
    }

    public eUsers(String name, String login, String password, String email) {
       this.name = name;
       this.login = login;
       this.password = password;
       this.email = email;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.name;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<eRoles> arr = new ArrayList<>();
        arr.add(new eRoles());
        return arr;
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
        this.name = login;
    }

    @Override
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


