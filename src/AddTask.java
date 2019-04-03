

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AddTask")
public class AddTask extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       

    public AddTask() 
    {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");
        String task = request.getParameter("task");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Cookie[] cookies = null;
        cookies = request.getCookies();
        String uname = cookies[1].getValue();


        String insertSql = " INSERT INTO " + uname + " (id, TITLE, OBJECT) VALUES (default,?,?)";

        try {
            DBConnection.getDBConnection();
            connection = DBConnection.connection;
            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, task);
            System.out.println(insertSql.toString());
            preparedStatement.execute();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("data", new String("Success!"));           
	   	 RequestDispatcher view = request.getRequestDispatcher("template.jsp");      
	   	 view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		doGet(request, response);
	}

}
