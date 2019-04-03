

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Profile() 
    {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		  Cookie[] cookies = request.getCookies();
		  String uname = cookies[1].getValue();
		
		  Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      PrintWriter out = response.getWriter();
	      try 
	      {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;

	         String selectSQL = "SELECT * FROM techexUsers WHERE USERNAME='" + uname +  "'";
	         preparedStatement = connection.prepareStatement(selectSQL);

	         System.out.println(preparedStatement.toString());
	         ResultSet rs = preparedStatement.executeQuery();
	         String profile = "";

	         if(rs.next())
	         {
	             String fname = rs.getString("FIRST_NAME").trim();
	             String lname = rs.getString("LAST_NAME").trim();
	             String email = rs.getString("EMAIL").trim();
	             
		         profile += "<h1>Profile</h1>";
		         profile += "<p>Hello " + lname + ", " + fname + "";
		         profile += "<p>Your email is " + email + "";
	         }
	         
	         rs.close();
	         preparedStatement.close();
	         connection.close();
        	 
        	 request.setAttribute("data", profile);
        	 RequestDispatcher view = request.getRequestDispatcher("template.jsp");      
        	 view.forward(request, response);
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		doGet(request, response);
	}

}
