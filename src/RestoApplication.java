import dao.DishDao;
import dao.IngredientDao;
import entity.Dish;
import entity.Ingredient;

import java.sql.SQLException;
import java.util.List;

public class RestoApplication {
    public static void main(String[] args) throws SQLException {


        IngredientDao ingredientDao = new IngredientDao();
        List<Ingredient> ingredients = ingredientDao.getAll(2,2);
        System.out.println(ingredients);
    }
}