package hei.school.ingredient_again_why.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IngredientExceptionMissingParams extends RuntimeException{
    public IngredientExceptionMissingParams (String message){
        super(message);
    }
}