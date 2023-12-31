package lk.ijse.gdse65.miniproject65.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse65.miniproject65.db.DBProcess;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "customer",urlPatterns = "/customer",
        initParams = {
           @WebInitParam(name = "db-user",value = "root"),
           @WebInitParam(name = "db-pw",value = "mysql"),
           @WebInitParam(name = "db-url",value = "jdbc:mysql://localhost:3306/gdse65JavaEE?createDatabaseIfNotExist=true"),
           @WebInitParam(name = "db-class",value = "com.mysql.cj.jdbc.Driver")
        }

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
//
//            System.out.println(url);
//            System.out.println(user);
//            System.out.println(password);
//
//            System.out.println("------------PARMS------------------");

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
        var data = new DBProcess();
        writer.println(data.saveCustomerData(name,city,email,connection));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var writer = resp.getWriter();
        String id = req.getParameter("id");
        if(id == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }else {

        resp.setContentType("text/html");
        var data = new DBProcess();
            var getData = data.getCustomerData(id, connection);
            for (String eachData : getData){
                writer.println(eachData+"\n");
            }

        }
    }
}
