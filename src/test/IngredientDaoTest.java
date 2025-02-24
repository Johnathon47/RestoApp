package test;

import dao.IngredientDao;
import entity.Ingredient;
import entity.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientDaoTest {
    IngredientDao subject = new IngredientDao();

    @Test
    void read_all_ingredient_ok() {
        Ingredient expecteIngredient = saucisse();

        List<Ingredient> actual = subject.getAll(1,1);

        assertTrue(actual.contains(expecteIngredient));
        //assertEquals(expecteIngredient, actual);
    }

    private Ingredient saucisse() {
        Ingredient expectedIngredient = new Ingredient();

        expectedIngredient.setId(1);
        expectedIngredient.setName("Saucisse");
        expectedIngredient.setUnitPrice(BigDecimal.valueOf(20));
        expectedIngredient.setUnit(Unit.G);
        expectedIngredient.setUpdateDateTime(Date.valueOf("2025-01-01"));

        return expectedIngredient;
    }
}
