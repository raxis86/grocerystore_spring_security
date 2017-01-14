package grocerystore.services.security;

import grocerystore.domain.abstracts.IRepositoryUserSec;
import grocerystore.domain.entities.RoleSec;
import grocerystore.domain.entities.UserSec;
import grocerystore.domain.exceptions.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by raxis on 13.01.2017.
 */
//@Service("userDetailsService")
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private IRepositoryUserSec userSec;

    public UserDetailsServiceImpl(IRepositoryUserSec userSec){
        this.userSec=userSec;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserSec user;
        try {
            user = userSec.getOneByEmail(email); //our own User model class
        } catch (UserException e) {
            logger.error("cant getOneByEmail",e);
            throw new UsernameNotFoundException("Пользователь не найден!");
        }

        if(user!=null){
            //String password = Tool.computeHash(Tool.computeHash(user.getPassword()) + user.getSalt());
            String password = user.getPassword();
            //additional information on the security object
            boolean enabled = user.getStatus().equals(UserSec.Status.ACTIVE);
            boolean accountNonExpired = user.getStatus().equals(UserSec.Status.ACTIVE);
            boolean credentialsNonExpired = user.getStatus().equals(UserSec.Status.ACTIVE);
            boolean accountNonLocked = user.getStatus().equals(UserSec.Status.ACTIVE);

            //Let's populate user roles
            Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            for(RoleSec role : user.getRoles()){
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

            }

            //Now let's create Spring Security User object
            org.springframework.security.core.userdetails.User securityUser = new
                    org.springframework.security.core.userdetails.User(email, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
            return securityUser;
        }else{
            throw new UsernameNotFoundException("User Not Found!!!");
        }
    }
}
