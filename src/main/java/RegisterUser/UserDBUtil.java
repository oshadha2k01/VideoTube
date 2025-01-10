package RegisterUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDBUtil {

	// validation for username and password
	public static boolean validate(String userName, String password) {
		String sql = "select uPassword from user_registration where uName = ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, userName);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String storedPassword = rs.getString("uPassword");
					return PasswordUtil.matches(password, storedPassword);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	 // Retrieve a list of User details based on the provided username
	public static List<User> getUser(String userName) {
		ArrayList<User> user = new ArrayList<>();
		String sql = "select * from user_registration where uName = ?";

		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, userName);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int uID = rs.getInt(1);
					String uFirstName = rs.getString(2);
					String uLastName = rs.getString(3);
					String uName = rs.getString(4);
					String uPassword = rs.getString(5);
					String uEmail = rs.getString(6);
					String uPhoneNo = rs.getString(7);

					User usr = new User(uID, uFirstName, uLastName, uName, uPassword, uEmail, uPhoneNo);
					user.add(usr);
				}		  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
		
	}
	
	
	//Insert data into the database
	
	public static boolean insertuser(String FirstName, String LastName, String UserName, String Password, String Email, String PhoneNo) {

		String sql = "insert into user_registration (uFirstName, uLastName, uName, uPassword, uEmail, uPhoneNo) values (?, ?, ?, ?, ?, ?)";
		String hashedPassword = PasswordUtil.hashPassword(Password);

		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, FirstName);
			stmt.setString(2, LastName);
			stmt.setString(3, UserName);
			stmt.setString(4, hashedPassword);
			stmt.setString(5, Email);
			stmt.setString(6, PhoneNo);

			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		}
	
	  //Update user details in the database
	   
	public static boolean updateuser(String uID, String uFirstName, String uLastName, String uName, String uPassword, String uEmail, String uPhoneNo ) {
		boolean shouldUpdatePassword = uPassword != null && !uPassword.trim().isEmpty();
		String sqlWithPassword = "update user_registration set uFirstName=?, uLastName=?, uName=?, uPassword=?, uEmail=?, uPhoneNo=? where uID=?";
		String sqlWithoutPassword = "update user_registration set uFirstName=?, uLastName=?, uName=?, uEmail=?, uPhoneNo=? where uID=?";

		try (Connection con = DBConnect.getConnection()) {
			if (shouldUpdatePassword) {
				try (PreparedStatement stmt = con.prepareStatement(sqlWithPassword)) {
					stmt.setString(1, uFirstName);
					stmt.setString(2, uLastName);
					stmt.setString(3, uName);
					stmt.setString(4, PasswordUtil.hashPassword(uPassword));
					stmt.setString(5, uEmail);
					stmt.setString(6, uPhoneNo);
					stmt.setInt(7, Integer.parseInt(uID));
					return stmt.executeUpdate() > 0;
				}
			}

			try (PreparedStatement stmt = con.prepareStatement(sqlWithoutPassword)) {
				stmt.setString(1, uFirstName);
				stmt.setString(2, uLastName);
				stmt.setString(3, uName);
				stmt.setString(4, uEmail);
				stmt.setString(5, uPhoneNo);
				stmt.setInt(6, Integer.parseInt(uID));
				return stmt.executeUpdate() > 0;
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		
		return false;
		
		
	}
	
	//Retrieve data from database
	
       public static List<User> getUserDetails(String Id) {
		
		int convertedID = Integer.parseInt(Id);
		
		ArrayList<User> usr = new ArrayList<>();
		String sql = "select * from user_registration where uID = ?";
		
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, convertedID);
			try (ResultSet rs = stmt.executeQuery()) {
				while(rs.next()) {
					int uID = rs.getInt(1);
					String uFirstName = rs.getString(2);
					String uLastName = rs.getString(3);
					String uName = rs.getString(4);
					String uPassword = rs.getString(5);
					String uEmail = rs.getString(6);
					String uPhoneNo = rs.getString(7);
				
					User ur = new User(uID, uFirstName, uLastName, uName, uPassword, uEmail, uPhoneNo );
					usr.add(ur);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return usr;
	}

	// Retrieve users using pagination
	public static List<User> getUsersPaginated(int offset, int limit) {
		ArrayList<User> users = new ArrayList<>();
		String sql = "select * from user_registration order by uID asc limit ? offset ?";

		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int uID = rs.getInt(1);
					String uFirstName = rs.getString(2);
					String uLastName = rs.getString(3);
					String uName = rs.getString(4);
					String uPassword = rs.getString(5);
					String uEmail = rs.getString(6);
					String uPhoneNo = rs.getString(7);

					User usr = new User(uID, uFirstName, uLastName, uName, uPassword, uEmail, uPhoneNo);
					users.add(usr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
       
       //Delete user details  from database
       
       public static boolean deleteUser(String id) {
    	   int convId = Integer.parseInt(id);
    	   String sql = "delete from user_registration where uID = ?";
    	   
    	   try (Connection con = DBConnect.getConnection();
    			   PreparedStatement stmt = con.prepareStatement(sql)) {
    		   stmt.setInt(1, convId);
   			   int r = stmt.executeUpdate();
	           return r > 0;
    	   }
    	   catch (Exception e) {
    		   e.printStackTrace();
    	   }
    	   return false;
    	    
       }
	
	

	
	
	
	

}
