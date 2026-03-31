package hei.school.ingredient_again_why.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import hei.school.ingredient_again_why.DataSource;

@Component
public class IngredientRepository {
    DataSource dataSource = new DataSource();
    
    public ResultSet getIngredientsResultSet () {
        try (Connection connection = dataSource.getDataSourceConnection()){
            String str = 
            """
                SELECT i.id AS ingredient_id, i.name AS ingredient_name, i.price AS ingredient_price, 
                i.category AS ingredient_category FROM "Ingredient" AS i 
                """;
            PreparedStatement preparedStatement = connection.prepareStatement(str);
            ResultSet resultSet = preparedStatement.executeQuery();
            dataSource.closeTheConnection(connection);
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getIngredientsResultSetById (Integer id){
        try (Connection connection = dataSource.getDataSourceConnection()){
            String str = 
            """
                SELECT i.id AS ingredient_id, i.name AS ingredient_name, i.price AS ingredient_price, 
                i.category AS ingredient_category FROM "Ingredient" AS i
                WHERE i.id = ?
                """;
            PreparedStatement preparedStatement = connection.prepareStatement(str);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getStockByIngredientId (Integer id) {
        try (Connection connection = dataSource.getDataSourceConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT sm.id stock_id, quantity, type, unit, creation_datetime FROM \"StockMouvement\" AS sm WHERE id_ingredient = ?"
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            dataSource.closeTheConnection(connection);
            return resultSet;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
