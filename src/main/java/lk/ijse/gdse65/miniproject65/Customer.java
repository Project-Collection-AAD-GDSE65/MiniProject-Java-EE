package lk.ijse.gdse65.miniproject65;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "customer",urlPatterns = "/customer",
        initParams = {
           @WebInitParam(name = "db-user",value = "root"),
           @WebInitParam(name = "db-pw",value = "mysql"),
           @WebInitParam(name = "db-url",value = "jdbc:mysql://localhost:3306/gdse65JavaEE?createDatabaseIfNotExist=true"),
           @WebInitParam(name = "db-class",value = "com.mysql.cj.jdbc.Driver")
        },
        loadOnStartup = 5


)
public class Customer extends HttpServlet {
    Connection connection;

    private static final String SAVE_DATA = "INSERT INTO CustomerNew (NAME,CITY,EMAIL) VALUES (?,?,?)";
    private static final String GET_DATA = "SELECT * FROM CustomerNew WHERE id = ?";


    @Override
    public void init() throws ServletException {
        try {
            var user = getServletConfig().getInitParameter("db-user");
            var password = getServletConfig().getInitParameter("db-pw");
            var url = getServletConfig().getInitParameter("db-url");
            Class.forName(getServletConfig().getInitParameter("db-class"));
            this.connection = DriverManager.getConnection(url, user, password);

            System.out.println(url);
            System.out.println(user);
            System.out.println(password);

            System.out.println("------------PARMS------------------");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // param catch
        var name = req.getParameter("name");
        var city = req.getParameter("city");
        var email = req.getParameter("email");

        var writer = resp.getWriter();
        resp.setContentType("text/html");


        // save / manipulate data
        try {
            var ps = connection.prepareStatement(SAVE_DATA);
            ps.setString(1, name);
            ps.setString(2, city);
            ps.setString(3, email);

            if (ps.executeUpdate() != 0) {
                writer.println("Data saved");
            } else {
                writer.println("Failed to save data");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if(id == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }else {

        var writer = resp.getWriter();
        resp.setContentType("text/html");
        try {
            var ps = connection.prepareStatement(GET_DATA);
            ps.setInt(1, Integer.parseInt(id));
            var rs = ps.executeQuery();
            while (rs.next()){
                int custId = rs.getInt("id");
                String name = rs.getString("name");
                String city = rs.getString("city");
                String email = rs.getString("email");

                System.out.println(custId);
                System.out.println(name);
                System.out.println(city);
                System.out.println(email);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }


    }
}
