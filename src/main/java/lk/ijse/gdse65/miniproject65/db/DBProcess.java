package lk.ijse.gdse65.miniproject65.db;

import lk.ijse.gdse65.miniproject65.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBProcess {

    private static final String SAVE_DATA = "INSERT INTO CustomerNew (NAME,CITY,EMAIL) VALUES (?,?,?)";
    private static final String GET_DATA = "SELECT * FROM CustomerNew WHERE id = ?";

    final static Logger logger = LoggerFactory.getLogger(DBProcess.class);

    private static final String SAVE_ITEM_DATA = "INSERT INTO ItemNew (CODE,DESCR,QTY,UNITPRICE) VALUES (?,?,?,?)";
    public String saveCustomerData(String name, String city, String email, Connection connection){
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

    public List<String> getCustomerData(String id, Connection connection){
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

    public void saveItem(List<ItemDTO> items,Connection connection){
          String customItemId = "IT "+UUID.randomUUID();
            for(ItemDTO itemData : items){
                try {
                    var ps = connection.prepareStatement(SAVE_ITEM_DATA);
                    ps.setString(1, customItemId);
                    ps.setString(2, itemData.getDesc());
                    ps.setInt(3, itemData.getQty());
                    ps.setDouble(4, itemData.getUnitPrice());

                    if (ps.executeUpdate() != 0) {

                        System.out.println("Data saved");
                    } else {

                        System.out.println("Failed to save");

                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
    }
    public void saveItemOne(ItemDTO items,Connection connection){
        String customItemId = "IT "+UUID.randomUUID();
            try {
                var ps = connection.prepareStatement(SAVE_ITEM_DATA);
                ps.setString(1, customItemId);
                ps.setString(2, items.getDesc());
                ps.setInt(3, items.getQty());
                ps.setDouble(4, items.getUnitPrice());

                if (ps.executeUpdate() != 0) {
                    logger.info("Data saved");
                    System.out.println("Data saved");
                } else {
                    logger.error("Failed to save");
                    System.out.println("Failed to save");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

