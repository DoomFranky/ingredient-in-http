package hei.school.ingredient_again_why;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    String url = System.getenv("url");
    String user = System.getenv("user");
    String password = System.getenv("password");

    public Connection getDataSourceConnection (){
        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            return connection;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void closeTheConnection (Connection connection){
        try{
            if (connection!=null) {
                connection.close();
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
