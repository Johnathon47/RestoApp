package test;

import entity.Dish;
import entity.DishIngredient;
import entity.Ingredient;
import entity.IngredientPrice;
import entity.Unit;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StaticDataSource {

    public static void main(String[] args) {

        // Création des ingrédients
        Ingredient saucisse = new Ingredient(1L, "Saucisse");
        Ingredient huile = new Ingredient(2L, "Huile");
        Ingredient pain = new Ingredient(3L, "Pain");
        Ingredient oeuf = new Ingredient(4L, "Oeuf");

        // Définition de leur prix actuel
        saucisse.setPriceHistory(List.of(new IngredientPrice(1L, BigDecimal.valueOf(20), Timestamp.valueOf("2025-01-01 00:00:00"), Unit.G, saucisse)));
        huile.setPriceHistory(List.of(new IngredientPrice(2L, BigDecimal.valueOf(10000), Timestamp.valueOf("2025-01-01 00:00:00"), Unit.L, huile)));
        pain.setPriceHistory(List.of(new IngredientPrice(3L, BigDecimal.valueOf(1000), Timestamp.valueOf("2025-01-01 00:00:00"), Unit.U, pain)));
        oeuf.setPriceHistory(List.of(new IngredientPrice(4L, BigDecimal.valueOf(1000), Timestamp.valueOf("2025-01-01 00:00:00"), Unit.U, oeuf)));

        // Construction des DishIngredient avec quantité + unité
        DishIngredient s = new DishIngredient(saucisse, BigDecimal.valueOf(100), Unit.G);
        DishIngredient h = new DishIngredient(huile, BigDecimal.valueOf(0.15), Unit.L);
        DishIngredient p = new DishIngredient(pain, BigDecimal.valueOf(1), Unit.U);
        DishIngredient o = new DishIngredient(oeuf, BigDecimal.valueOf(1), Unit.U);

        // Construction du plat
        List<DishIngredient> ingredientList = new ArrayList<>();
        ingredientList.add(s);
        ingredientList.add(h);
        ingredientList.add(p);
        ingredientList.add(o);

        Dish dish = new Dish(1, "Hot dog", ingredientList);

        // Affichage du coût total
        System.out.println("Coût total du plat '" + dish.getName() + "' : " + dish.getIngredientCost() + " Ar");
    }
}

