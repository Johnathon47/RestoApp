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

        assertTrue(actual.contains(expecteDishAvailability));
    }

    private DishAvailability availability_hot_dog() {
        DishAvailability expectedDishAvailability = new DishAvailability();
        Dish expectedDish = new Dish();
        List<DishIngredient> expectedDishIngredients = new ArrayList<>();

        Ingredient ingredient1 = new Ingredient(1,"Saucisse", new BigDecimal("20"), Unit.G, Timestamp.valueOf("2025-01-01 00:00:00"));
        Ingredient ingredient2 = new Ingredient(2,"Huile", new BigDecimal("10000"), Unit.L, Timestamp.valueOf("2025-01-01 00:00:00"));
        Ingredient ingredient3 = new Ingredient(3,"Pain", new BigDecimal("1000"), Unit.U, Timestamp.valueOf("2025-01-01 00:00:00"));
        Ingredient ingredient4 = new Ingredient(4,"Oeuf", new BigDecimal("1000"), Unit.U, Timestamp.valueOf("2025-02-28 00:00:00"));

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
