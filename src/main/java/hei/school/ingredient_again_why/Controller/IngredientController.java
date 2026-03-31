package hei.school.ingredient_again_why.Controller;

import java.time.temporal.Temporal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hei.school.ingredient_again_why.Entity.Ingredients;
import hei.school.ingredient_again_why.Entity.StockMouvement;
import hei.school.ingredient_again_why.Entity.Unit_type;
import hei.school.ingredient_again_why.Exception.IngredientExceptionMissingParams;
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
        }
        catch (Exception e) {
            return ResponseEntity.status(500)
                .body(e.getMessage());
        } 
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Integer id){      
        Ingredients ingredient = ingredientService.getIngredientsById(id);
          
        try {
            return ResponseEntity
                .status(200)
                .body(ingredient);
        } catch (IngredientExceptionNotFound e) {
            return ResponseEntity
                .status(404)
                .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(e.getMessage());
        }
    }

    @GetMapping("/ingredients/{id}/stock")
    public ResponseEntity<?> getIngredientStockById(@PathVariable Integer id,@RequestParam Temporal at,@RequestParam Unit_type unit){
        List<StockMouvement> listOfStock = ingredientService.getStockByIngredientsId(id,at,unit);

        try {
            return ResponseEntity
                .status(200)
                .body(listOfStock);
        } catch (IngredientExceptionNotFound e) {
            return ResponseEntity
                .status(404)
                .body(e.getMessage());
        } catch (IngredientExceptionMissingParams e){
            return ResponseEntity
                .status(400)
                .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(e.getMessage());
        }
    }

}
