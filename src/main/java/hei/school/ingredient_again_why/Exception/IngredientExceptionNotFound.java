package hei.school.ingredient_again_why.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IngredientExceptionNotFound extends RuntimeException{
    public IngredientExceptionNotFound (String message){
        super(message);
    }
}
