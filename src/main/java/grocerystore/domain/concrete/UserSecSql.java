package grocerystore.domain.concrete;

import grocerystore.domain.abstracts.IRepositoryUserSec;
import grocerystore.domain.entities.RoleSec;
import grocerystore.domain.entities.UserSec;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.domain.exceptions.RoleException;
import grocerystore.domain.exceptions.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static grocerystore.constants.Constants.*;
/**
 * Created by raxis on 13.01.2017.
 */
@Repository
public class UserSecSql extends SQLImplementation implements IRepositoryUserSec {
    private static final Logger logger = LoggerFactory.getLogger(UserSecSql.class);

    private void fillUser(UserSec usr, ResultSet resultSet) throws SQLException, UserException {
        usr.setId(UUID.fromString(resultSet.getString("ID")));
        usr.setEmail(resultSet.getString("EMAIL"));
        usr.setPassword(resultSet.getString("PASSWORD"));
        usr.setStatus(UserSec.Status.valueOf(resultSet.getString("STATUS")));
        usr.setName(resultSet.getString("NAME"));
        usr.setLastname(resultSet.getString("LASTNAME"));
        usr.setSurname(resultSet.getString("SURNAME"));
        usr.setAddress(resultSet.getString("ADDRESS"));
        usr.setPhone(resultSet.getString("PHONE"));

        RoleSecSql roleSecSql = new RoleSecSql();
        try {
            usr.setRoles(roleSecSql.getAllByUserId(UUID.fromString(resultSet.getString("ID"))));
        } catch (RoleException e) {
            logger.error("cant fillUserRoleList",e);
            throw new UserException("Проблема с базой данных: невозможно получить записи из таблицы пользователей!",e);
        }
    }

    @Override
    public List<UserSec> getAll() throws UserException {
        List<UserSec> userList = new ArrayList<>();
        try(Connection connection = getDs().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(USERSEC_SELECTALL_QUERY);) {
            while (resultSet.next()){
                UserSec usr = new UserSec();
                fillUser(usr,resultSet);
                userList.add(usr);
            }
        } catch (SQLException e) {
            logger.error("Select Users error!",e);
            throw new UserException("Проблема с базой данных: невозможно получить записи из таблицы пользователей!",e);
        }
        return userList;
    }

    @Override
    public UserSec getOne(UUID id) throws UserException {
        UserSec usr = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USERSEC_PREP_SELECTONE_QUERY);) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                usr = new UserSec();
                fillUser(usr,resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne User!",e);
            throw new UserException("Проблема с базой данных: невозможно получить запись из таблицы пользователей!",e);
        }
        return usr;
    }

    @Override
    public boolean create(UserSec entity) throws UserException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USERSEC_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getEmail());
            statement.setObject(3,entity.getPassword());
            statement.setObject(4,entity.getStatus());
            statement.setObject(5,entity.getName());
            statement.setObject(6,entity.getLastname());
            statement.setObject(7,entity.getSurname());
            statement.setObject(8,entity.getAddress());
            statement.setObject(9,entity.getPhone());
            statement.execute();
        } catch (SQLException e) {
            logger.error("Insert user error!", e);
            throw new UserException("Проблема с базой данных: невозможно создать запись в таблице пользователей!",e);
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) throws UserException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USERSEC_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            logger.error("Cant delete User!",e);
            throw new UserException("Проблема с базой данных: невозможно удалить запись из таблицы пользователей!",e);
        }
        return true;
    }

    @Override
    public boolean update(UserSec entity) throws UserException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement=connection.prepareStatement(USERSEC_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getEmail());
            statement.setObject(2,entity.getPassword());
            statement.setObject(3,entity.getStatus());
            statement.setObject(4,entity.getName());
            statement.setObject(5,entity.getLastname());
            statement.setObject(6,entity.getSurname());
            statement.setObject(7,entity.getAddress());
            statement.setObject(8,entity.getPhone());
            statement.setObject(9,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cant update User!",e);
            throw new UserException("Проблема с базой данных: невозможно изменить запись в таблице пользователей!",e);
        }
        return true;
    }

    @Override
    public UserSec getOne(String email, String passwordHash) throws UserException {
        UserSec usr = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USERSEC_PREP_SELECTONE_BY_AUTH_QUERY)) {
            statement.setObject(1,email);
            statement.setObject(2,passwordHash);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                usr = new UserSec();
                fillUser(usr,resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne User by auth info",e);
            throw new UserException("Проблема с базой данных: невозможно получить запись из таблицы пользователей!",e);
        }
        return usr;
    }

    @Override
    public UserSec getOneByEmail(String email) throws UserException {
        UserSec usr = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USERSEC_PREP_SELECTONE_BY_EMAIL_QUERY)) {
            statement.setObject(1,email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                usr = new UserSec();
                fillUser(usr,resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne User by auth info",e);
            throw new UserException("Проблема с базой данных: невозможно получить запись из таблицы пользователей!",e);
        }
        return usr;
    }

    @Override
    public List<UserSec> getAllByRoleId(UUID id) throws UserException {
        List<UserSec> userList = new ArrayList<>();
        UserSec usr = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USERSEC_PREP_SELECTALL_BY_ROLEID_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                usr = new UserSec();
                fillUser(usr,resultSet);
                userList.add(usr);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getAllByUserId(UUID id) Role!", e);
            throw new UserException("Проблема с базой данных: невозможно получить запись из таблицы пользователей!",e);
        }
        return userList;
    }
}
