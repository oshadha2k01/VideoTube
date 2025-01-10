package service;

import RegisterUser.User;
import RegisterUser.UserDBUtil;

public class AuthService {

	private final UserService userService = new UserService();

	public User login(String userName, String password) {
		boolean valid = UserDBUtil.validate(userName, password);
		if (!valid) {
			return null;
		}
		return userService.getUserByUserName(userName);
	}

	public User register(String firstName, String lastName, String userName, String password, String email, String phoneNo) {
		boolean created = UserDBUtil.insertuser(firstName, lastName, userName, password, email, phoneNo);
		if (!created) {
			return null;
		}
		return userService.getUserByUserName(userName);
	}
}
