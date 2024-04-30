package bruno.spring.java.services;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import bruno.spring.java.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	UserRepository repository;

	public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name" + username +"!");
        var user = repository.findByUserName(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found ");
        }
    }
}
