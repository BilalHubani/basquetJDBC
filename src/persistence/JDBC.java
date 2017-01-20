package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by dam on 20/1/17.
 */
public class JDBC {
    private Connection connection;
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/baskets";
        String usr = "root";
        String pass = "";
        connection = DriverManager.getConnection(url, usr, pass);
    }
    public void desconectar() throws SQLException{
        if(connection != null){
            connection.close();
        }
    }
}
