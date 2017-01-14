package grocerystore.domain.concrete;

import grocerystore.domain.abstracts.IRepositoryRoleSec;
import grocerystore.domain.entities.RoleSec;
import grocerystore.domain.exceptions.RoleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static grocerystore.constants.Constants.*;

/**
 * Created by raxis on 13.01.2017.
 */
public class RoleSecSql extends SQLImplementation implements IRepositoryRoleSec {
    private static final Logger logger = LoggerFactory.getLogger(RoleSecSql.class);

    @Override
    public List<RoleSec> getAll() throws RoleException {
        List<RoleSec> roleList = new ArrayList<>();
        try(Connection connection = getDs().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(ROLESEC_SELECTALL_QUERY);) {
            while (resultSet.next()){
                RoleSec role = new RoleSec();
                role.setId(UUID.fromString(resultSet.getString("ID")));
                role.setRoleName(resultSet.getString("ROLENAME"));
                roleList.add(role);
            }
        } catch (SQLException e) {
            logger.error("cant getAll",e);
            throw new RoleException("Проблема с базой данных: невозможно получить записи из таблицы ролей!",e);
        }
        return roleList;
    }

    @Override
    public RoleSec getOne(UUID id) throws RoleException {
        RoleSec role = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(ROLESEC_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                role = new RoleSec();
                role.setId(UUID.fromString(resultSet.getString("ID")));
                role.setRoleName(resultSet.getString("ROLENAME"));

            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne Role!", e);
            throw new RoleException("Проблема с базой данных: невозможно получить запись из таблицы ролей!",e);
        }
        return role;
    }

    @Override
    public boolean create(RoleSec entity) throws RoleException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(ROLESEC_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getRoleName());
            statement.execute();
        } catch (SQLException e) {
            logger.error("cant create",e);
            throw new RoleException("Проблема с базой данных: невозможно создать запись в таблице ролей!",e);
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) throws RoleException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(ROLESEC_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            logger.error("cant delete",e);
            throw new RoleException("Проблема с базой данных: невозможно удалить запись из таблицы ролей!",e);
        }
        return true;
    }

    @Override
    public boolean update(RoleSec entity) throws RoleException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement=connection.prepareStatement(ROLESEC_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getRoleName());
            statement.setObject(2,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("cant update",e);
            throw new RoleException("Проблема с базой данных: невозможно изменить запись в таблице ролей!",e);
        }
        return true;
    }

    @Override
    public RoleSec roleByRoleName(String roleName) throws RoleException {
        RoleSec role = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(ROLESEC_PREP_SELECTONE_BY_NAME_QUERY)) {
            statement.setObject(1,roleName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                role = new RoleSec();
                role.setId(UUID.fromString(resultSet.getString("ID")));
                role.setRoleName(resultSet.getString("ROLENAME"));

            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne Role!", e);
            throw new RoleException("Проблема с базой данных: невозможно получить запись из таблицы ролей!",e);
        }
        return role;
    }

    @Override
    public List<RoleSec> getAllByUserId(UUID id) throws RoleException {
        List<RoleSec> roleSecList = new ArrayList<>();
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(ROLESEC_PREP_SELECTALL_BY_USERID_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                RoleSec role = new RoleSec();
                role.setId(UUID.fromString(resultSet.getString("ID")));
                role.setRoleName(resultSet.getString("ROLENAME"));
                roleSecList.add(role);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getAllByUserId(UUID id) Role!", e);
            throw new RoleException("Проблема с базой данных: невозможно получить запись из таблицы ролей!",e);
        }
        return roleSecList;
    }
}
