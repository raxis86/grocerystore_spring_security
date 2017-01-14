package grocerystore.domain.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 13.01.2017.
 */
public class RoleSec {
    private static final Logger logger = LoggerFactory.getLogger(RoleSec.class);

    private UUID id;            //первичный ключ
    private String roleName;    //наименование
    private List<UserSec> users;//список пользователей

    public RoleSec(){}

    public RoleSec(UUID id, String roleName, List<UserSec> users){
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

    public List<UserSec> getUsers() {
        return users;
    }

    public void setUsers(List<UserSec> users) {
        this.users = users;
    }
}
