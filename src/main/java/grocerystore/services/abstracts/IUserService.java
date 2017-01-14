package grocerystore.services.abstracts;

import grocerystore.domain.entities.UserSec;
import grocerystore.services.exceptions.FormUserException;
import grocerystore.services.exceptions.UserServiceException;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IUserService {
    public UserSec formUser(String email, String password, String name,
                            String lastname, String surname,
                            String address, String phone, String role) throws UserServiceException, FormUserException;
    public UserSec formUserFromRepo(String email, String password) throws UserServiceException, FormUserException;
    public UserSec formUserFromRepo(String email) throws UserServiceException, FormUserException;
    public void updateUser(UserSec user, String name, String lastname,
                           String surname, String address, String phone) throws UserServiceException;
}
