package grocerystore.services.concrete;

import grocerystore.domain.abstracts.IRepositoryRoleSec;
import grocerystore.domain.abstracts.IRepositoryUserSec;
import grocerystore.domain.entities.RoleSec;
import grocerystore.domain.entities.UserSec;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.services.abstracts.IAccountService;
import grocerystore.services.exceptions.AccountServiceException;
import grocerystore.services.models.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by raxis on 29.12.2016.
 * Сервис для регистрации и аутентификации пользователя
 */
@Service
public class AccountService implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private IRepositoryUserSec userHandler;
    private IRepositoryRoleSec roleHandler;

    public AccountService(IRepositoryUserSec userHandler, IRepositoryRoleSec roleHandler){
        this.userHandler=userHandler;
        this.roleHandler=roleHandler;
    }

    /**
     * Метод для аутентификации пользователя
     * @param user
     * @return
     */
    @Override
    public AuthUser logIn(UserSec user) throws AccountServiceException {
        /*RoleSec role = null;
        try {
            role = roleHandler.getOne(user.getRoles().get(0));
        } catch (DAOException e) {
            logger.error("cant logIn",e);
            throw new AccountServiceException("Невозможно осуществить вход в систему!",e);
        }
        return new AuthUser(user,role);*/
        return null;
    }

    /**
     * Метод для регистрации пользователя
     * @param user
     * @return
     * @throws DAOException
     */
    @Override
    public AuthUser signIn(UserSec user) throws AccountServiceException {
        RoleSec role = null;
        try {
            userHandler.create(user);
            role = roleHandler.getOne(user.getRoles().get(0).getId());
        } catch (DAOException e) {
            logger.error("cant signIn!",e);
            throw new AccountServiceException("Невозможно зарегистрировать пользователя!",e);
        }

        return new AuthUser(user,role);
    }

}
