package ictgradschool;

import java.util.List;

public interface UserDao {
	public List<User> getAllUsers();

	public User getUser(String nName);

	public void updateUser(User user);

	public void deleteUser(User user);
}
