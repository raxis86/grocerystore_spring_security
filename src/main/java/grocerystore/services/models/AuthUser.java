package grocerystore.services.models;

import grocerystore.domain.entities.RoleSec;
import grocerystore.domain.entities.UserSec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raxis on 29.12.2016.
 * Класс для хранения пользователя и роли
 */
public class AuthUser {
    private static final Logger logger = LoggerFactory.getLogger(AuthUser.class);

    private UserSec user;
    private RoleSec role;

    public AuthUser(UserSec user, RoleSec role){
        this.user=user;
        this.role=role;
    }

    public UserSec getUser() {
        return user;
    }

    public void setUser(UserSec user) {
        this.user = user;
    }

    public RoleSec getRole() {
        return role;
    }

    public void setRole(RoleSec role) {
        this.role = role;
    }

}
