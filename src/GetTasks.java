import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetTasks")
public class GetTasks extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GetTasks() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html; charset=UTF-8");
        Cookie[] cookies = null;
        cookies = request.getCookies();
        String uname = cookies[1].getValue();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            DBConnection.getDBConnection();
            connection = DBConnection.connection;

            String selectSQL = "SELECT * FROM " + uname + "";
            preparedStatement = connection.prepareStatement(selectSQL);

            System.out.println(preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();
            String task = "";

            ArrayList<String> arr = new ArrayList<String>();
            if (!rs.next())
            {
            	task += "<p><p><a href=\"addTask.html\">Add task</a>";
                rs.close();
                preparedStatement.close();
                connection.close();
                
            	request.setAttribute("data", "<h1>No tasks created</h1>");
                RequestDispatcher view = request.getRequestDispatcher("template3.jsp");
                view.forward(request, response);
                
            }
            
            else
            {
                arr.add(Integer.toString(rs.getInt("id")));
                arr.add(rs.getString("DT").trim());
                arr.add(rs.getString("TITLE").trim());
                arr.add(rs.getString("OBJECT").trim());

	            while (rs.next()) {
	            	arr.add(Integer.toString(rs.getInt("id")));
	                arr.add(rs.getString("DT").trim());
	                arr.add(rs.getString("TITLE").trim());
	                arr.add(rs.getString("OBJECT").trim());
	            }

            rs.close();
            preparedStatement.close();
            connection.close();

            request.setAttribute("data", arr);
            RequestDispatcher view = request.getRequestDispatcher("template3.jsp");
            view.forward(request, response);

            
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se2) {}

            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        doGet(request, response);
    }
}