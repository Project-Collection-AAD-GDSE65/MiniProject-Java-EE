package lk.ijse.gdse65.miniproject65.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse65.miniproject65.db.DBProcess;
import lk.ijse.gdse65.miniproject65.dto.ItemDTO;
import org.eclipse.yasson.internal.model.JsonbCreator;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "item",urlPatterns = "/item"
//        initParams = {
//                @WebInitParam(name = "db-user",value = "root"),
//                @WebInitParam(name = "db-pw",value = "mysql"),
//                @WebInitParam(name = "db-url",value = "jdbc:mysql://localhost:3306/gdse65JavaEE?createDatabaseIfNotExist=true"),
//                @WebInitParam(name = "db-class",value = "com.mysql.cj.jdbc.Driver")
//        }
)
public class Item extends HttpServlet {
    Connection connection;
    public void init() throws ServletException {

        try {
//            var user = getServletConfig().getInitParameter("db-user");
//            var password = getServletConfig().getInitParameter("db-pw");
//            var url = getServletConfig().getInitParameter("db-url");
//            Class.forName(getServletConfig().getInitParameter("db-class"));
//            this.connection = DriverManager.getConnection(url, user, password);
            InitialContext ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/pos");
            System.out.println(pool);
            this.connection = pool.getConnection();

        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getContentType() == null ||
                !req.getContentType().toLowerCase().startsWith("application/json")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }else {
            Jsonb jsonb = JsonbBuilder.create();
            var itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            var dbProcess = new DBProcess();
            dbProcess.saveItemOne(itemDTO,connection);

//            List<ItemDTO> itemList= jsonb.fromJson(req.getReader(),new ArrayList<ItemDTO>(){
//            }.getClass().getGenericSuperclass());
//            var dbProcess = new DBProcess();
//            dbProcess.saveItem(itemList,connection);
//            //itemList.forEach(System.out::println);
//            jsonb.toJson(itemList,resp.getWriter());
        }




    }
}
