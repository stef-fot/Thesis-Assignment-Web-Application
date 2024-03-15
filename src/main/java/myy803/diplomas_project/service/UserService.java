package myy803.diplomas_project.service;

import org.springframework.stereotype.Service;
import myy803.diplomas_project.model.User;


@Service
public interface UserService {
	public void saveUser(User user) ;
    public boolean isUserPresent(User user);
}
