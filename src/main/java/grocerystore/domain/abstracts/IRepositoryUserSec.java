package grocerystore.domain.abstracts;

import grocerystore.domain.entities.UserSec;
import grocerystore.domain.exceptions.UserException;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 13.01.2017.
 */
public interface IRepositoryUserSec extends IRepository<UserSec,UUID> {
    public UserSec getOne(String email, String passwordHash) throws UserException;
    public UserSec getOneByEmail(String email) throws UserException;
    public List<UserSec> getAllByRoleId(UUID id) throws UserException;
}
