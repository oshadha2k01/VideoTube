package service;

import java.util.ArrayList;
import java.util.List;

import RegisterUser.User;
import RegisterUser.UserDBUtil;

public class UserService {

	public User getUserByUserName(String userName) {
		List<User> users = UserDBUtil.getUser(userName);
		if (users == null || users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	public User getUserById(int userId) {
		List<User> users = UserDBUtil.getUserDetails(String.valueOf(userId));
		if (users == null || users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	public List<User> getUsersPaginated(int offset, int limit) {
		if (offset < 0) {
			offset = 0;
		}
		if (limit <= 0) {
			limit = 10;
		}
		if (limit > 100) {
			limit = 100;
		}
		List<User> users = UserDBUtil.getUsersPaginated(offset, limit);
		return users == null ? new ArrayList<User>() : users;
	}
}
