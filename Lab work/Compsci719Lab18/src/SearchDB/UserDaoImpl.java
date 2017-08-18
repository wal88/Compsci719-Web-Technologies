package SearchDB;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDaoImpl implements UserDao {

	List<User> users;

	public UserDaoImpl() {
		users = new ArrayList<User>();
		// User user1 = new User("Robert", "bobson", "bigbob", "123@gmail.com");
		// User user2 = new User("John", "bob", "smallbob",
		// "thelad@hotmail.com");
		// users.add(user1);
		// users.add(user2);
	}

	public void addUser(User user) {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");

			/*
			 * try { Class cd =
			 * Class.forName("org.apache.derby.jdbc.ClientDriver"); try
			 * (Connection conn = DriverManager .getConnection(
			 * "jdbc:derby://localhost:1527/Lab19Ex3;create=true", "APP",
			 * "APP")) { // you use the connection here try (Statement stmt =
			 * conn.createStatement(); ResultSet rs =
			 * stmt.executeQuery("SELECT * FROM Users")) { while (rs.next()) {
			 * int numColumns = rs.getMetaData().getColumnCount();
			 * 
			 * String[] columnNames = new String[]{"First Name: ",
			 * "Last Name: ", "Nickname: ", "Email: "};
			 * 
			 * for (int i = 1; i <= numColumns; i++) {
			 * System.out.println(columnNames[i-1] + "\n" + rs.getObject(i)); }
			 * System.out.println(); } }
			 * 
			 * } catch (SQLException e) { e.printStackTrace(); } } catch
			 * (Exception except) { except.printStackTrace(); }
			 */

			try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Lab18Ex03_user;create=true",
					"APP", "APP")) {
				// you use the connection here

				try (Statement stmt = conn.createStatement()) {
					stmt.executeUpdate("INSERT INTO users VALUES ('" + user.getfName() + "','" + user.getlName() + "','"
							+ user.getnName() + "','" + user.getemail() + "') ");
				}

			} // with 'try-resource' the VM will take care of closing the
				// connection
			catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (Exception except) {
			except.printStackTrace();
		}

	}

	@Override
	public List<User> getAllUsers() {

		return users;
	}

	@Override
	public User getUser(String nName) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getnName().equals(nName)) {
				return users.get(i);
			}
		}
		return null;
	}

	@Override
	public void updateUser(User user) {
		for (User userX : users) {
			if (userX.getnName().equals(user.getnName())) {
				userX.setfName(user.getfName());
				userX.setlName(user.getlName());
				userX.setemail(user.getemail());
				System.out.println(user.getnName() + " user info updated");
			}
		}

	}

	@Override
	public void deleteUser(User user) {
		for (User userX : users) {
			if (userX.getnName().equals(user.getnName())) {
				users.remove(userX);
				System.out.println(user.getnName() + " user removed");
			}

		}
	}

}
