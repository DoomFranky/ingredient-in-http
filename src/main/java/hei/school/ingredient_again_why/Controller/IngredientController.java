package hei.school.ingredient_again_why.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hei.school.ingredient_again_why.Entity.Ingredients;
import hei.school.ingredient_again_why.Exception.IngredientExceptionNotFound;
import hei.school.ingredient_again_why.Service.IngredientService;

@RestController
public class IngredientController {
    IngredientService ingredientService = new IngredientService();

    @GetMapping("/ingredients")
    public ResponseEntity<?> getIngredients(){
        List<Ingredients> listOfIngredient = ingredientService.getIngredients();
        try {
            return ResponseEntity
                .status(200)
                .body(listOfIngredient);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(e.getMessage());
        } 
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Integer id){      
        Ingredients ingredient = ingredientService.getIngredientsById(id);
          
        try {
            if (ingredient==null) {
                throw new IngredientExceptionNotFound("Ingredient.id={"+id+"} is not found");
            }
            return ResponseEntity
                .status(200)
                .body(ingredient);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(e.getMessage());
        }
    }

    public ResponseEntity<?> getIngredientStockById(){
        throw new RuntimeException("methode not implemented");
    }

}
