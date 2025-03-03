import dao.DishDao;
import dao.IngredientDao;
import entity.Dish;
import entity.DishIngredient;
import entity.Ingredient;
import entity.Unit;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class RestoApplication {
    public static void main(String[] args) throws SQLException {

        //Timestamp date_potato = Timestamp.valueOf("2025-02-28 06:00:00.0");
        //BigDecimal unitPrice = new BigDecimal(3);
        //IngredientDao ingredientDao = new IngredientDao();
        //Ingredient newingredient = new Ingredient(5,"Pomme de terre", unitPrice, Unit.G, date_potato);
        //List<Ingredient> ingredients = ingredientDao.getAll(1,5);
        //ingredients.add(newingredient);
        //List<Ingredient> ingredientsUpdate = ingredientDao.saveAll(ingredients);
        DishDao dishDao = new DishDao();
        /*
        System.out.println(list);

        Ingredient ingredient1 = new Ingredient(6,"Tomate", new BigDecimal("200"), Unit.U, Timestamp.valueOf("2025-02-28 10:00:00.0"));
        Ingredient ingredient2 = new Ingredient(7,"Fromage", new BigDecimal("50"), Unit.G, Timestamp.valueOf("2025-02-28 11:00:00.0"));
        DishIngredient dishIngredient1 = new DishIngredient(ingredient1,new BigDecimal("10"),Unit.U);
        DishIngredient dishIngredient2 = new DishIngredient(ingredient2, new BigDecimal("1000"), Unit.G);

        Dish dish = new Dish(3,"Pizza",new BigDecimal("5000"), Arrays.asList(dishIngredient1, dishIngredient2));

        dishDao.saveAll(Arrays.asList(dish));*/

        //dishDao.deleteOperation(3);

    }
}