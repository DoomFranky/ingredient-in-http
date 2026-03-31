package hei.school.ingredient_again_why.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hei.school.ingredient_again_why.Entity.CategoryEnum;
import hei.school.ingredient_again_why.Entity.Dish;
import hei.school.ingredient_again_why.Entity.DishIngredient;
import hei.school.ingredient_again_why.Entity.DishTypeEnum;
import hei.school.ingredient_again_why.Entity.Ingredients;
import hei.school.ingredient_again_why.Entity.Unit_type;
import hei.school.ingredient_again_why.Repository.DishRepository;

public class DishService {
    DishRepository dishRepository = new DishRepository();
    
    public List<Dish> getDish (){
        ResultSet resultSet = dishRepository.getDishesResultSet();
        List<Dish> listOfDish = new ArrayList<>();
        try {
            while(resultSet.next()){
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("dish_id"));
                dish.setName(resultSet.getString("dish_name"));
                dish.setDishType(DishTypeEnum.valueOf(resultSet.getString("dish_type").toUpperCase()));
                dish.setDishPrice(resultSet.getDouble("dish_price"));
                dish.setDishIngredients(getIngredientsByDishId(resultSet.getInt("dish_id")));
                listOfDish.add(dish);
            }
                
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfDish;
    }
    
    private List<DishIngredient> getIngredientsByDishId (Integer id){
        ResultSet resultSet = dishRepository.getIngredientsByDishIdResultSet(id);
        List<DishIngredient> listOfIngredients = new ArrayList<>();
        try {
            while(resultSet.next()){
                DishIngredient dishIngredient = new DishIngredient();
                dishIngredient.setId(id);
                dishIngredient.setQuantity_require(resultSet.getDouble("quantity_required"));
                dishIngredient.setUnit_type(Unit_type.valueOf(resultSet.getString("unit")));
                Ingredients ingredient = new Ingredients();
                ingredient.setId(resultSet.getInt("ingredient_id"));
                ingredient.setName(resultSet.getString("ingredient_name"));
                ingredient.setPrice(resultSet.getDouble("ingredient_price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("ingredient_category")));
                ingredient.setStockMouvementList(null);
                dishIngredient.setIngredeint(ingredient);
                listOfIngredients.add(dishIngredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfIngredients;
    }

}
