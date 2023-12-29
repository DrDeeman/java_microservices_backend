package entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class eRoles implements GrantedAuthority {

    @Id
    private int id;

    public int getId() {
        return 1;
    }
    @Override
    public String getAuthority() {
        return "ROLE_USER";
    }
}
