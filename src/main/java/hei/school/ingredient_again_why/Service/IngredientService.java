package hei.school.ingredient_again_why.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import hei.school.ingredient_again_why.Entity.CategoryEnum;
import hei.school.ingredient_again_why.Entity.Ingredients;
import hei.school.ingredient_again_why.Entity.MovementTypeEnum;
import hei.school.ingredient_again_why.Entity.StockMouvement;
import hei.school.ingredient_again_why.Entity.StockValue;
import hei.school.ingredient_again_why.Entity.Unit_type;
import hei.school.ingredient_again_why.Exception.IngredientExceptionMissingParams;
import hei.school.ingredient_again_why.Exception.IngredientExceptionNotFound;
import hei.school.ingredient_again_why.Repository.IngredientRepository;

@Component
public class IngredientService {
    IngredientRepository ingredientRepository = new IngredientRepository();
    
    public List<Ingredients> getIngredients() {
        ResultSet resultSet = ingredientRepository.getIngredientsResultSet();
        List<Ingredients> listOfIngredients = new ArrayList<>();
        try {
            while(resultSet.next()){
                Ingredients ingredient = new Ingredients();
                ingredient.setId(resultSet.getInt("ingredient_id"));
                ingredient.setName(resultSet.getString("ingredient_name"));
                ingredient.setPrice(resultSet.getDouble("ingredient_price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("ingredient_category")));
                ingredient.setStockMouvementList(null);
                listOfIngredients.add(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfIngredients;
    }

    public Ingredients getIngredientsById (Integer id){
        ResultSet resultSet = ingredientRepository.getIngredientsResultSetById(id);
        Ingredients ingredient =  new Ingredients();
        try {
            if (resultSet.next()) {
                ingredient.setId(resultSet.getInt("ingredient_id"));
                ingredient.setName(resultSet.getString("ingredient_name"));
                ingredient.setPrice(resultSet.getDouble("ingredient_price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("ingredient_category")));
                ingredient.setStockMouvementList(null);
            }
            if(ingredient.getId()==null){
                throw new IngredientExceptionNotFound("Ingredient.id={"+id+"} is not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredient;
    }

    public List<StockMouvement> getStockByIngredientsId(Integer id,Temporal at, Unit_type unit) {
        if (at == null || unit == null) {
            throw new IngredientExceptionMissingParams("Either mandatory query parameter `at` or `unit` is not provided.");
        }
        ResultSet resultSet = ingredientRepository.getStockByIngredientId(id,at,unit);
        List<StockMouvement> stockMouvements = new ArrayList<>();
        try {
            while(resultSet.next()){
                StockMouvement stockMouvement = new StockMouvement();
                stockMouvement.setId(resultSet.getInt("stock_id"));
                stockMouvement.setValue(new StockValue(resultSet.getDouble("quantity"), Unit_type.valueOf(resultSet.getString("unit"))));
                stockMouvement.setType(MovementTypeEnum.valueOf(resultSet.getString("type")));
                stockMouvement.setCreationDateTime(resultSet.getTimestamp("creation_datetime").toInstant());
                stockMouvements.add(stockMouvement);
            }
            if (stockMouvements.getFirst()==null) {
                throw new IngredientExceptionNotFound("Ingredient.id={"+id+"} is not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockMouvements;
    }
}
