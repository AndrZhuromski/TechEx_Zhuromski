
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


@WebServlet("/Authorize")
public class Authorize extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Authorize() 
   {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {
      String uname = request.getParameter("uname");
      String psw = request.getParameter("pswrd");

      /*_____________________________*/
      
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try 
      {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;

         String selectSQL = "SELECT * FROM techexUsers WHERE USERNAME='" + uname + "' AND PASSWORD='" + psw + "'";
         preparedStatement = connection.prepareStatement(selectSQL);

         System.out.println(preparedStatement.toString());
         ResultSet rs = preparedStatement.executeQuery();


         if(rs.next())
         {
        	 /*
        	 HttpSession oldSession = request.getSession(false);
             if (oldSession != null) 
             {
                 oldSession.invalidate();
                 
                 Cookie[] cookies = request.getCookies();
                 if(cookies!=null)
                 for (int i = 0; i < cookies.length; i++) 
                 {
                	 cookies[i].setMaxAge(0);
                 }
             }
             //generate a new session
             HttpSession newSession = request.getSession(true);

             newSession.setMaxInactiveInterval(30);
			*/
             Cookie message = new Cookie("uname", uname);
             response.addCookie(message);  
        	 response.sendRedirect("Profile");
         }
         
         else
         {
        	 request.setAttribute("data", new String("Invalid login or password. Please try again!"));           
        	 RequestDispatcher view = request.getRequestDispatcher("template.jsp");      
        	 view.forward(request, response);
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
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {
      doGet(request, response);
   }

}