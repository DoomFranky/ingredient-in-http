package hei.school.ingredient_again_why.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hei.school.ingredient_again_why.Entity.Dish;
import hei.school.ingredient_again_why.Service.DishService;

@RestController
public class DishController {
    DishService dishService = new DishService();

    @GetMapping("/dishes")
    public ResponseEntity<?> getDishes () {
        List<Dish> listOfDish = dishService.getDish();
        try {
            return ResponseEntity
                .status(200)
                .body(listOfDish);
        }
        catch (Exception e) {
            return ResponseEntity.status(500)
                .body(e.getMessage());
        } 
    }

    public ResponseEntity<?> getDishIngredientById () {
        throw new RuntimeException("mehtode not implemented");
    }
}
