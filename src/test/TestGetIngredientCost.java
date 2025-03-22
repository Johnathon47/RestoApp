package test;

import entity.Dish;
import entity.DishIngredient;
import entity.Ingredient;
import entity.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGetIngredientCost {

    @Test
    void testGetIngredientCost() {
        Ingredient saucisse = new Ingredient(1, "Saucisse", BigDecimal.valueOf(20), Unit.G,(Timestamp.valueOf("2025-01-01 00:00:00.0")));
        Ingredient huile = new Ingredient(2, "Huile", BigDecimal.valueOf(10000), Unit.L,(Timestamp.valueOf("2025-01-01 00:00:00.0")));
        Ingredient pain = new Ingredient(3, "Oeuf", BigDecimal.valueOf(1000), Unit.U,(Timestamp.valueOf("2025-01-01 00:00:00.0")));
        Ingredient oeuf = new Ingredient(4, "Pain", BigDecimal.valueOf(1000), Unit.U,(Timestamp.valueOf("2025-01-01 00:00:00.0")));

        DishIngredient s = new DishIngredient(saucisse,BigDecimal.valueOf(100),Unit.G);
        DishIngredient h = new DishIngredient(huile,BigDecimal.valueOf(0.15),Unit.L);
        DishIngredient p = new DishIngredient(pain,BigDecimal.valueOf(1),Unit.U);
        DishIngredient o = new DishIngredient(oeuf,BigDecimal.valueOf(1),Unit.U);

        List<DishIngredient> ingredientList = new ArrayList<>();
        ingredientList.add(s);
        ingredientList.add(h);
        ingredientList.add(p);
        ingredientList.add(o);
        Dish dish = new Dish(1,"Hot dog",ingredientList);
        BigDecimal expectedCost = new BigDecimal("5500.00");

        BigDecimal actualCost = dish.getIngredientCost();

        assertEquals(expectedCost, actualCost, "Erreur : Valeur attendue = "+ expectedCost+", mais obtenue = "+ actualCost);

    }
}
