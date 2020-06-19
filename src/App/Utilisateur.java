package App;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import Security.HashPassword;

public class Utilisateur {
	Connection myConnection; 
	String user="root";
	String pass="";
	String url="jdbc:mysql://localhost:3308/appdb";
	
	
	public Utilisateur() {
		//etape 1 : tester l'accessibilite de driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Changement du driver est OK");
		} 
		catch (ClassNotFoundException e) {
			System.out.println("Changement du driver est ... Non");
			System.exit(0);
		}
		
		//etape 2 : connecter a la Base de donnees
		try {
			myConnection=DriverManager.getConnection(url,user,pass);
			System.out.println("Base de donnees accessible Ok");
		}catch(SQLException e) {
			System.out.println("Base de donnees accessible Non");
		}
	}

	public boolean testUser(String username,String password) throws SQLException {
		String sql="SELECT * FROM users";
		Statement st;
		st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
		boolean isUser=false;
		
		while(rs.next()) {
			try {
				//tester le nom d'utilisateur et le hash de mot de passe entre par l'utilisateur avec les infos dans le base de donnees 
				if(rs.getString(3).equals(username) && rs.getString(4).equals(HashPassword.hashPassword(password))) { 
					isUser=true;
				}
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Problem d'algorithme de Securite");
				isUser=false;
			}
		}
		
		return isUser;
	}
	
	//tester le reponse donnee par utilisateur d'un question avec le vrai reponse au base de donnees
	public boolean testAnswer(int questionId,String answer) throws SQLException {
		String sql="SELECT * FROM questions";
		Statement st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
		
		while(rs.next()) {
			if(rs.getInt(1)==questionId && rs.getString(4).equals(answer)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean testResultName(String username) throws SQLException {
		String sql="SELECT * FROM results";
		Statement st;
		st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
		boolean isUser=false;
		
		while(rs.next()) {
			//tester si le nom d'utilisateur est deja existe dans la base de donnees
			if(rs.getString(2).equals(username)) { 
				isUser=true;
			}
			
		}
		
		return isUser;
	}
	
	//retourne un question au base de donnees en utilisant l'id si le question n'existe pas retourne 'none'
	public String getQuestionById(int id) throws SQLException {
		String sql="SELECT * FROM questions";
		Statement st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
		
		while(rs.next()) {
			if(rs.getInt(1)==id) {
				return rs.getString(2);
			}
		}
		
		return "none";
	}
	
	//retourne les choix d'un question au base de donnees en utilisant l'id si le question n'existe pas retoure 'none'
	public String getChoicesById(int id) throws SQLException {
		String sql="SELECT * FROM questions";
		Statement st = myConnection.createStatement();
		ResultSet rs=st.executeQuery(sql);
		
		while(rs.next()) {
			if(rs.getInt(1)==id) {
				return rs.getString(3);
			}
		}
		
		return "none";
	}
	
	//inserer nouveau utilisateur au base de donnees
	public void insertUser(String fullname,String username,String password) throws SQLException {
		String sql="INSERT INTO users (fullname,username,password) VALUES(?,?,?)";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setString(1,fullname);
		st.setString(2, username);
		st.setString(3, password);
		
		int nb=st.executeUpdate();
		System.out.println(nb+" inserted");
	}
	
	public void insertResult(String username, int note) throws SQLException {
		String sql="INSERT INTO results (username,note) VALUES(?,?)";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setString(1,username);
		st.setInt(2, note);
		int nb=st.executeUpdate();
		System.out.println(nb+" inserted");
	}
	
	public void editResult(String username, int note) throws SQLException {
		String sql="UPDATE results SET note=? WHERE username=?";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setInt(1, note);
		st.setString(2,username);
		int nb=st.executeUpdate();
		System.out.println(nb+" updated");
	}
	
	//supprimer un utilisateur de base de donnees
	public void deleteUser(String username) throws SQLException {
		String sql="DELETE FROM users WHERE username=?";
		PreparedStatement st=myConnection.prepareStatement(sql);
		st.setString(1, username);
		
		int nb=st.executeUpdate();
		System.out.println(nb+" Deleted");
	}
}

