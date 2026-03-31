package hei.school.ingredient_again_why.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hei.school.ingredient_again_why.DataSource;

public class DishRepository {
    DataSource dataSource = new DataSource();
    public ResultSet getDishesResultSet (){
        try (Connection connection = dataSource.getDataSourceConnection()){
            String str = 
            "SELECT d.id AS dish_id ,d.name AS dish_name, dish_type, d.\"DishPrice\" AS dish_price FROM \"Dish\" AS d";
            PreparedStatement preparedStatement = connection.prepareStatement(str);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getIngredientsByDishIdResultSet (Integer id){
        try (Connection connection = dataSource.getDataSourceConnection()){
            String str = 
            "SELECT i.id AS ingredient_id, i.name AS ingredient_name, i.price AS ingredient_price, "+
            "i.category AS ingredient_category, quantity_required, unit, di.id AS dish_ingredient_id FROM \"Ingredient\" AS i "+
            "JOIN \"DishIngredient\" AS di ON i.id = di.id_ingredient WHERE di.id_dish = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(str);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
