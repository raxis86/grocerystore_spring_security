package grocerystore.domain.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 13.01.2017.
 */
public class User {
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    private UUID id;            //первичный ключ
    private String email;       //email
    private String password;    //пароль
    private Status status;      //статус пользователя
    private String name;        //имя
    private String lastname;    //фамилия
    private String surname;     //отчество
    private String address;     //адрес
    private String phone;       //телефон
    private List<Role> roles;//список ролей

    public static enum Status {
        ACTIVE, INACTIVE
    }

    public User() {}

    public User(UUID id, String email, String password, Status status,
                String name, String lastname, String surname,
                String address, String phone, List<Role> roles){
        this.id=id;
        this.password=password;
        this.status=status;
        this.email=email;
        this.name=name;
        this.lastname=lastname;
        this.surname=surname;
        this.address=address;
        this.phone=phone;
        this.roles=roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
