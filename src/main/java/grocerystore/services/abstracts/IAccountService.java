package grocerystore.services.abstracts;

import grocerystore.domain.entities.UserSec;
import grocerystore.services.exceptions.AccountServiceException;
import grocerystore.services.models.AuthUser;


/**
 * Created by raxis on 29.12.2016.
 */
public interface IAccountService {
    public AuthUser logIn(UserSec user) throws AccountServiceException;
    public AuthUser signIn(UserSec user) throws AccountServiceException;
}
