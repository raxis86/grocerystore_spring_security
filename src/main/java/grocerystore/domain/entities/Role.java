package grocerystore.domain.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 13.01.2017.
 */
public class Role {
    private static final Logger logger = LoggerFactory.getLogger(Role.class);

    private UUID id;            //первичный ключ
    private String roleName;    //наименование
    private List<User> users;//список пользователей

    public Role(){}

    public Role(UUID id, String roleName, List<User> users){
        this.id=id;
        this.roleName=roleName;
        this.users=users;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
