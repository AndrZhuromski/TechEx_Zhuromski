
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServletDB_Zhuromski")
public class MyServletDB_Zhuromski extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public MyServletDB_Zhuromski() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM MYSQL_DBTABLE_Zhuromski";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM MYSQL_DBTABLE_Zhuromski WHERE EMAIL='" + keyword + "'";
            preparedStatement = connection.prepareStatement(selectSQL);
         }
         System.out.println(preparedStatement.toString());
         ResultSet rs = preparedStatement.executeQuery();
         
         
         // for one result
         if(!keyword.isEmpty())
         {
	         if(rs.next())
	         {
	        	 int id = rs.getInt("id");
	             String fname = rs.getString("FIRST_NAME").trim();
	             String lname = rs.getString("LAST_NAME").trim();
	             String email = rs.getString("EMAIL").trim();
	             String phone = rs.getString("PHONE").trim();

	                out.println("ID: " + id + ", ");
	                out.println("FIRST_NAME: " + fname + ", ");
	                out.println("LAST_NAME: " + lname + ", ");
	                out.println("EMAIL: " + email + ", ");
	                out.println("PHONE: " + phone + "<br>");
	         }
         }
         
         else
         {
	         while (rs.next()) {
	            int id = rs.getInt("id");
	            String fname = rs.getString("FIRST_NAME").trim();
	             String lname = rs.getString("LAST_NAME").trim();
	             String email = rs.getString("EMAIL").trim();
	             String phone = rs.getString("PHONE").trim();
	
	            if (keyword.isEmpty() || email.contains(keyword)) {
	            	out.println("ID: " + id + ", ");
	                out.println("FIRST_NAME: " + fname + ", ");
	                out.println("LAST_NAME: " + lname + ", ");
	                out.println("EMAIL: " + email + ", ");
	                out.println("PHONE: " + phone + "<br>");
	            }
	         }
         }
         out.println("<a href=/webproject_Q2_Zhuromski_0219/search_Zhuromski.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
