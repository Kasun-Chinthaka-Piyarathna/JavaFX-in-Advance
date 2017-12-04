package sample;

import java.sql.*;
import javax.swing.*;

/**
 * Kasun Chinthaka
 */

public class ConnectionUtil {
    Connection conn = null;

    public static Connection connectdb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javafx_test", "root", "password");
        } catch (Exception e) {
            return null;
        }
    }
}