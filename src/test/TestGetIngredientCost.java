package test;

import entity.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetIngredientCost {

    @Test
    void testGetIngredientCost() {
        // Création des ingrédients sans prix direct
        Ingredient saucisse = new Ingredient(1L, "Saucisse");
        Ingredient huile = new Ingredient(2L, "Huile");
        Ingredient pain = new Ingredient(3L, "Oeuf");
        Ingredient oeuf = new Ingredient(4L, "Pain");

        // Ajout de l'historique des prix
        saucisse.setPriceHistory(List.of(
                new IngredientPrice(1L, BigDecimal.valueOf(20), Unit.G, Timestamp.valueOf("2025-04-19 01:10:40.0"))
        ));

        huile.setPriceHistory(List.of(
                new IngredientPrice(2L, BigDecimal.valueOf(10000), Unit.L, Timestamp.valueOf("2025-01-01 00:00:00.0"))
        ));

        pain.setPriceHistory(List.of(
                new IngredientPrice(3L, BigDecimal.valueOf(1000), Unit.U, Timestamp.valueOf("2025-01-01 00:00:00.0"))
        ));

        oeuf.setPriceHistory(List.of(
                new IngredientPrice(4L, BigDecimal.valueOf(1000), Unit.U, Timestamp.valueOf("2025-01-01 00:00:00.0"))
        ));

        // Quantités utilisées pour le plat
        DishIngredient s = new DishIngredient(saucisse, BigDecimal.valueOf(100), Unit.G);
        DishIngredient h = new DishIngredient(huile, BigDecimal.valueOf(0.15), Unit.L);
        DishIngredient p = new DishIngredient(pain, BigDecimal.valueOf(1), Unit.U);
        DishIngredient o = new DishIngredient(oeuf, BigDecimal.valueOf(1), Unit.U);

        List<DishIngredient> ingredientList = new ArrayList<>();
        ingredientList.add(s);
        ingredientList.add(h);
        ingredientList.add(p);
        ingredientList.add(o);

        Dish dish = new Dish(1, "Hot dog", ingredientList);

        BigDecimal expectedCost = new BigDecimal("5500.00");
        BigDecimal actualCost = dish.getIngredientCost();

        assertEquals(expectedCost, actualCost, "Erreur : Valeur attendue = " + expectedCost + ", mais obtenue = " + actualCost);
    }
}
