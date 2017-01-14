package grocerystore.services.concrete;

import grocerystore.domain.abstracts.IRepositoryRole;
import grocerystore.domain.abstracts.IRepositoryUser;
import grocerystore.domain.entities.Role;
import grocerystore.domain.entities.User;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.domain.exceptions.RoleException;
import grocerystore.domain.exceptions.UserException;
import grocerystore.services.abstracts.IUserService;
import grocerystore.services.exceptions.FormUserException;
import grocerystore.services.exceptions.UserServiceException;
import grocerystore.services.exceptions.ValidateException;
import grocerystore.services.models.Message;
import grocerystore.services.validators.abstracts.IValidator;
import grocerystore.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 */
@Service
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private IRepositoryUser userHandler;
    private IRepositoryRole roleHandler;
    private IValidator nameValidator;
    private IValidator addressValidator;
    private IValidator passwordValidator;
    private IValidator emailValidator;

    public UserService(IRepositoryUser userHandler,
                       IRepositoryRole roleHandler,
                       IValidator nameValidator,
                       IValidator addressValidator,
                       IValidator passwordValidator,
                       IValidator emailValidator){
        this.userHandler=userHandler;
        this.roleHandler=roleHandler;
        this.nameValidator=nameValidator;
        this.addressValidator=addressValidator;
        this.passwordValidator=passwordValidator;
        this.emailValidator=emailValidator;
    }

    @Override
    public User formUser(String email, String password, String name,
                         String lastname, String surname, String address,
                         String phone, String roleName) throws UserServiceException, FormUserException {

        Message message = new Message();
        User user = new User();
        User userByEmail = null;
        Role roleByName = null;
        List<Role> roleList = new ArrayList<>();

        try {
            roleByName = roleHandler.roleByRoleName(roleName);
            roleList.add(roleByName);
            userByEmail = userHandler.getOneByEmail(email);
        } catch (UserException e) {
            logger.error("cant getOneByEmail",e);
            throw new UserServiceException("Невозможно определить пользователя!",e);
        } catch (RoleException e) {
            logger.error("cant role by name",e);
            throw new UserServiceException("Невозможно определить пользователя!",e);
        }


        try {
            emailValidator.validate(email);
            passwordValidator.validate(password);
        } catch (ValidateException e) {
            message.addErrorMessage(e.getMessage());
        }

        try {
            nameValidator.validate(name);
            nameValidator.validate(lastname);
            nameValidator.validate(surname);
            addressValidator.validate(address);
        } catch (ValidateException e) {
            message.addErrorMessage(e.getMessage());
        }

        if(userByEmail!=null){
            message.addErrorMessage("Пользователь с таким email уже существует в базе!");
        }

        if(roleByName==null){
            message.addErrorMessage("Роли с таким наименованием не существует!");
        }

        if(message.isOk()){
            user.setId(UUID.randomUUID());
            user.setEmail(email.toLowerCase());
            user.setPassword(Tool.computeHash(password));
            user.setStatus(User.Status.ACTIVE);
            user.setName(name);
            user.setLastname(lastname);
            user.setSurname(surname);
            user.setPhone(phone);
            user.setAddress(address);
            user.setRoles(roleList);
        }
        else {
            throw new FormUserException(message);
        }

        return user;
    }

    @Override
    public User formUserFromRepo(String email, String password) throws UserServiceException, FormUserException {
        //Ищем, что существует юзер с таким email
        Message message = new Message();
        User user;
        User userByEmail;

        try {
            userByEmail = userHandler.getOneByEmail(email);
        } catch (UserException e) {
            logger.error("cant getOneByEmail",e);
            throw new UserServiceException("Невозможно проверить пользователя!",e);
        }

        if(userByEmail==null){
            message.addErrorMessage("Пользователь с таким email не найден!");
            throw new FormUserException(message);
        }

        try {
            user = userHandler.getOne(email.toLowerCase(), Tool.computeHash(password));
        } catch (UserException e) {
            logger.error("cant getOn",e);
            throw new UserServiceException("Невозможно определить пользователя!",e);
        }

        if(user==null){
            message.addErrorMessage("Неверный пароль!");
            throw new FormUserException(message);
        }

        return user;
    }

    @Override
    public User formUserFromRepo(String email) throws UserServiceException, FormUserException {
        Message message = new Message();
        User user;

        try {
            user = userHandler.getOneByEmail(email);
        } catch (UserException e) {
            logger.error("cant getOneByEmail",e);
            throw new UserServiceException("Невозможно проверить пользователя!",e);
        }

        if(user==null){
            message.addErrorMessage("Пользователь с таким email не найден!");
            throw new FormUserException(message);
        }

        return user;
    }

    @Override
    public void updateUser(User user, String name, String lastname,
                           String surname, String address, String phone) throws UserServiceException {
        try {
            nameValidator.validate(name);
            nameValidator.validate(lastname);
            addressValidator.validate(address);
        } catch (ValidateException e) {
            throw new UserServiceException(e.getMessage(),e);
        }
        user.setName(name);
        user.setLastname(lastname);
        user.setSurname(surname);
        user.setAddress(address);
        user.setPhone(phone);

        try {
            userHandler.update(user);
        } catch (DAOException e) {
            logger.error("cant update user",e);
            throw new UserServiceException("Невозможно сохранить изменения!",e);
        }
    }
}
