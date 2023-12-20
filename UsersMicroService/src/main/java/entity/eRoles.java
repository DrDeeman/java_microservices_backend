package entity;

import jakarta.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class eRoles implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return "ROLE_USER";
    }
}
