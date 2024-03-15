 package myy803.diplomas_project.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import myy803.diplomas_project.dao.UserDAO;
import myy803.diplomas_project.model.User;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDAO userDAO;
	
	public UserServiceImpl() {
	}
	
	@Override
	public void saveUser(User user) {
		System.out.println("ServiceImpl phase " + user.getId());
		System.out.println("ServiceImpl phase " + user.getPassword());
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDAO.save(user);	
    }

	@Override
	public boolean isUserPresent(User user) {
		//Optional<User> storedUser = userDAO.findById(user.getId());
		Optional<User> storedUser = userDAO.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 return userDAO.findByUsername(username).orElseThrow(
	                ()-> new UsernameNotFoundException(
	                        String.format("USER_NOT_FOUND", username)
	                ));
	}
	
	public Optional<User> findById(int id) {
        return userDAO.findById(id);
    }
}
