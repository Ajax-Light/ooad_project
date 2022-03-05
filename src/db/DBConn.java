package db;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;

import gui.ErrorDialog;
import javafx.util.Pair;

public class DBConn {
	
	final String dbName = "movie_db";
	public static Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	int updateCount = 0;
	
	public DBConn(String host, int port, String user, String pass) {
		host = host.toLowerCase().strip();
		user = user.strip();
		pass = pass.strip();
		String connectString = "jdbc:mysql://"+host+":"+port+"/"+dbName;
		createConnection(connectString, user, pass);
	}
	
	public void createConnection(String connectString, String user, String pass) {
		try {
			/* Class.forName("com.mysql.jdbc.Driver"); 
			 * Deprecated, manual loading unnecessary
			 */

			con = DriverManager.getConnection(connectString, user, pass);
			System.out.println("Connection SUCESS!!");
			System.out.println("Connected to Catalog: "+con.getCatalog());
			System.out.println("As User: "+user);
		} catch (Exception e) {		
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			
			/* Pop-Up to show errors */
			ErrorDialog dialog = new ErrorDialog("Connection FAILED!!", errors.toString(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			System.exit(1);
		}
	}
	
	public void closeConnection() {
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Pair<ResultSet, Integer> executeQuery(String query) {
		try {
			
		    stmt = con.createStatement();
		    con.setAutoCommit(false);
		    if (stmt.execute(query)) {
		    	updateCount = 0;
		        rs = stmt.getResultSet();
		        con.commit();
		        return new Pair<ResultSet, Integer>(rs, 0);
		    } else {
		    	rs = null;
		    	updateCount = stmt.getUpdateCount();
		    	con.commit();
		    }

		}
		catch (SQLException ex){
		    // handle any errors
		    System.err.println("SQLException: " + ex.getMessage());
		    System.err.println("SQLState: " + ex.getSQLState());
		    System.err.println("VendorError: " + ex.getErrorCode());
		    ErrorDialog err = new ErrorDialog("SQL Query Execute Error", ex.getMessage(), false);
		    err.setVisible(true);
		    System.err.println("SQL Transaction is being rolled back");
		    try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/*
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed

		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore

		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		}
		*/
		try {
			stmt.close();
		} catch (SQLException e) {}
		
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new Pair<ResultSet, Integer>(rs, new Integer(updateCount));
	}
}
