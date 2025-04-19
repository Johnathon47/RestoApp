package test;

import dao.IngredientDao;
import entity.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IngredientDaoTest {
    IngredientDao subject = new IngredientDao();

    @Test
    void read_all_ingredient_ok() {
        List<Ingredient> actual = subject.getAll(1, 5);

        boolean containsSaucisse = actual.stream()
                .anyMatch(ingredient ->
                        ingredient.getId() == 1L &&
                                "Saucisse".equals(ingredient.getName())
                );

        assertTrue(containsSaucisse, "L'ingrédient 'Saucisse' avec l'id 1 n'a pas été trouvé.");
    }
}
