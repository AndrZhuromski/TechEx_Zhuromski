
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Registration")
public class Registration extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Registration() 
   {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {

	  PrintWriter out = response.getWriter();
      String uname = request.getParameter("uname");
      String fname = request.getParameter("fname");
      String lname = request.getParameter("lname");
      String email = request.getParameter("email");
      String passw = request.getParameter("pass1");
      String passw2 = request.getParameter("pass2");
      System.out.println(passw + " " + passw2 + " " + passw==passw2);

      /*_____________________________*/
      
      if (passw.equals(passw2))
      {
	      Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      try 
	      {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	
	         String selectSQL = "SELECT * FROM techexUsers WHERE USERNAME='" + uname + "'";
	         preparedStatement = connection.prepareStatement(selectSQL);
	
	         System.out.println(preparedStatement.toString());
	         ResultSet rs = preparedStatement.executeQuery();
	
	         // if username exist;
	         if(rs.next())
	         {
	        	 request.setAttribute("data", new String("Error. Username exists."));           
	        	 RequestDispatcher view = request.getRequestDispatcher("template.jsp");      
	        	 view.forward(request, response);
	        	 
	        	 return;
	         }
	
	         rs.close();
	         preparedStatement.close();
	         connection.close();
	      } 
	      
	      catch (SQLException se) 
	      {
	         se.printStackTrace();
	      } 
	      
	      catch (Exception e) 
	      {
	         e.printStackTrace();
	      } 
	      
	      finally 
	      {
	         try 
	         {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } 
	         
	         catch (SQLException se2) 
	         {
	         }
	         
	         try 
	         {
	            if (connection != null)
	               connection.close();
	         } 
	         
	         catch (SQLException se) 
	         {
	            se.printStackTrace();
	         }
	      }
	      
	      /*________________________________*/
	
	      
	      connection = null;
	      String insertSql = " INSERT INTO techexUsers (id, USERNAME,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD) VALUES (default,?,?,?,?,?);";
	      String createTable = "CREATE TABLE " + uname + " (ID INT NOT NULL AUTO_INCREMENT, DT DATE NOT NULL CURRENT_TIMESTAMP, TITLE varchar(50) NOT NULL, OBJECT varchar(200) NOT NULL, PRIMARY KEY (ID))";

	      try 
	      {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         
	         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
	         preparedStmt.setString(1, uname);
	         preparedStmt.setString(2, fname);
	         preparedStmt.setString(3, lname);
	         preparedStmt.setString(4, email);
	         preparedStmt.setString(5, passw);
	         preparedStmt.execute();
	         connection.close();
	         
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         preparedStmt = connection.prepareStatement(createTable);
	         System.out.println(preparedStmt.toString());
	         preparedStmt.execute();
	         connection.close();
	      } 
	      catch (Exception e) 
	      {
	         e.printStackTrace();
	      }
	      
	 
	      out.println("<meta http-equiv=\"refresh\" content=\"0; URL='/TechEx_Zhuromski/auth.html'\" />");
      }
      
      else
      {
    	 
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {
      doGet(request, response);
      
   }

}