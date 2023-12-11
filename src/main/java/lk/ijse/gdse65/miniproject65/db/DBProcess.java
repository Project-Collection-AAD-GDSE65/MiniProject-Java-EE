package lk.ijse.gdse65.miniproject65.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBProcess {

    private static final String SAVE_DATA = "INSERT INTO CustomerNew (NAME,CITY,EMAIL) VALUES (?,?,?)";
    private static final String GET_DATA = "SELECT * FROM CustomerNew WHERE id = ?";

    public String saveData(String name, String city, String email, Connection connection){
        // save / manipulate data
        try {
            var ps = connection.prepareStatement(SAVE_DATA);
            ps.setString(1, name);
            ps.setString(2, city);
            ps.setString(3, email);

            if (ps.executeUpdate() != 0) {
                return  "Data saved";
            } else {
                return "Failed to save data";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getData(String id, Connection connection){
        //get data
        List<String> selectedCustomer = new ArrayList<>();
        try {
            var ps = connection.prepareStatement(GET_DATA);
            ps.setInt(1, Integer.parseInt(id));
            var rs = ps.executeQuery();
            while (rs.next()){
                int custId = rs.getInt("id");
                String name = rs.getString("name");
                String city = rs.getString("city");
                String email = rs.getString("email");

                selectedCustomer.add(String.valueOf(custId));
                selectedCustomer.add(name);
                selectedCustomer.add(city);
                selectedCustomer.add(email);
            }
            return selectedCustomer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
