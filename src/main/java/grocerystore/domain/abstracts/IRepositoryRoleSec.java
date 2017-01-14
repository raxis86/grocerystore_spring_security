package grocerystore.domain.abstracts;

import grocerystore.domain.entities.RoleSec;
import grocerystore.domain.exceptions.RoleException;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 13.01.2017.
 */
public interface IRepositoryRoleSec extends IRepository<RoleSec,UUID> {
    public RoleSec roleByRoleName(String roleName) throws RoleException;
    public List<RoleSec> getAllByUserId(UUID id) throws RoleException;
}
