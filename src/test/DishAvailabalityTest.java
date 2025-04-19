package test;

import dao.DishAvailabailityDao;
import entity.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DishAvailabalityTest {
    DishAvailabailityDao subject = new DishAvailabailityDao();

    @Test
    void findDishAvailability() {
        DishAvailability expecteDishAvailability = availability_hot_dog();

        List<DishAvailability> actual = subject.findAll();

        assertEquals(expecteDishAvailability, actual);
    }

    private DishAvailability availability_hot_dog() {
        DishAvailability expectedDishAvailability = new DishAvailability();
        Dish expectedDish = new Dish();
        List<DishIngredient> expectedDishIngredients = new ArrayList<>();

        Ingredient ingredient1 = new Ingredient(1L,"Saucisse");
        Ingredient ingredient2 = new Ingredient(2L,"Huile");
        Ingredient ingredient3 = new Ingredient(3L,"Pain");
        Ingredient ingredient4 = new Ingredient(4L,"Oeuf");

        DishIngredient dishIngredient1 = new DishIngredient(ingredient1,new BigDecimal("100"),Unit.G);
        DishIngredient dishIngredient2 = new DishIngredient(ingredient2,new BigDecimal("0.15"),Unit.L);
        DishIngredient dishIngredient3 = new DishIngredient(ingredient3,new BigDecimal("1"),Unit.U);
        DishIngredient dishIngredient4 = new DishIngredient(ingredient4,new BigDecimal("1"),Unit.U);

        expectedDishIngredients.add(dishIngredient1);
        expectedDishIngredients.add(dishIngredient2);
        expectedDishIngredients.add(dishIngredient3);
        expectedDishIngredients.add(dishIngredient4);
        expectedDish.setId(1);
        expectedDish.setName("Hot dog");
        expectedDish.setIngredientList(expectedDishIngredients);

        expectedDishAvailability.setDish(expectedDish);
        expectedDishAvailability.setAvailability(20.0);

        return expectedDishAvailability;
    }
}
