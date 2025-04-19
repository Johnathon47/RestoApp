package dao;

import db.DataSource;
import entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class DishDao implements CrudOperations<Dish> {

    private final DataSource dataSource = new DataSource();
    private final IngredientPriceDao ingredientPriceDao = new IngredientPriceDao(); // Utilisé pour récupérer l'historique

    private void updateUnitPrice(Dish dish, Connection connection) throws SQLException {
        if (dish.getIngredientList().isEmpty()) {
            System.out.println("La liste des ingrédients est vide");
        }
        String updateQuery =
                """
                    UPDATE dish SET unit_price = ? WHERE id = ?;
                """;

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            BigDecimal calculatedPrice = dish.getIngredientCost(); // utilise getLatestPrice() des ingrédients
            updateStatement.setBigDecimal(1, calculatedPrice);
            updateStatement.setLong(2, dish.getId());
            updateStatement.executeUpdate();
        }
    }

    @Override
    public List<Dish> saveAll(List<Dish> dishes) {
        String dishQuery =
                """
                    INSERT INTO dish (id, name) VALUES (?, ?)
                    ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name;
                """;

        String dishIngredientQuery =
                """
                    INSERT INTO dish_ingredient (id_dish, id_ingredient, required_quantity, unit)
                    VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement dishStatement = connection.prepareStatement(dishQuery);
             PreparedStatement dishIngredientStatement = connection.prepareStatement(dishIngredientQuery)) {

            for (Dish dish : dishes) {
                dishStatement.setLong(1, dish.getId());
                dishStatement.setString(2, dish.getName());
                dishStatement.executeUpdate();

                for (DishIngredient dishIngredient : dish.getIngredientList()) {
                    dishIngredientStatement.setLong(1, dish.getId());
                    dishIngredientStatement.setLong(2, dishIngredient.getIngredient().getId());
                    dishIngredientStatement.setBigDecimal(3, dishIngredient.getRequiredQuantity());
                    dishIngredientStatement.setString(4, dishIngredient.getUnit().name());
                    dishIngredientStatement.executeUpdate();
                }

                updateUnitPrice(dish, connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishes;
    }

    @Override
    public List<Dish> getAll(int offset, int limit) {
        List<Dish> dishes = new ArrayList<>();
        Map<Long, Dish> dishMap = new HashMap<>();

        String sql =
                """
                    SELECT dish.id AS dish_id, dish.name AS dish_name, dish_ingredient.required_quantity, dish_ingredient.unit,
                           ingredient.id AS id_ingredient, ingredient.name AS ingredient_name
                    FROM dish
                    INNER JOIN dish_ingredient ON dish.id = dish_ingredient.id_dish
                    INNER JOIN ingredient ON dish_ingredient.id_ingredient = ingredient.id
                    ORDER BY dish.id LIMIT ? OFFSET ?;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, (offset - 1) * limit);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long dishId = resultSet.getLong("dish_id");
                    Dish dish = dishMap.get(dishId);

                    if (dish == null) {
                        dish = new Dish();
                        dish.setId(dishId);
                        dish.setName(resultSet.getString("dish_name"));
                        dish.setIngredientList(new ArrayList<>());
                        dishMap.put(dishId, dish);
                        dishes.add(dish);
                    }

                    Ingredient ingredient = new Ingredient();
                    Long ingredientId = resultSet.getLong("id_ingredient");
                    ingredient.setId(ingredientId);
                    ingredient.setName(resultSet.getString("ingredient_name"));

                    // Historique des prix
                    List<IngredientPrice> priceHistory = ingredientPriceDao.findAllForIngredient(ingredientId);
                    ingredient.setPriceHistory(priceHistory);

                    BigDecimal requiredQuantity = resultSet.getBigDecimal("required_quantity");
                    String unit = resultSet.getString("unit");

                    DishIngredient dishIngredient = new DishIngredient(ingredient, requiredQuantity, Unit.valueOf(unit));
                    dish.getIngredientList().add(dishIngredient);
                }

                for (Dish dish : dishes) {
                    updateUnitPrice(dish, connection);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des plats", e);
        }

        return dishes;
    }

    @Override
    public Dish findById(long dishId) {
        String sql =
                """
                    SELECT dish.id AS dish_id, dish.name AS dish_name, dish_ingredient.required_quantity, dish_ingredient.unit,
                           ingredient.id AS id_ingredient, ingredient.name AS ingredient_name
                    FROM dish
                    INNER JOIN dish_ingredient ON dish.id = dish_ingredient.id_dish
                    INNER JOIN ingredient ON dish_ingredient.id_ingredient = ingredient.id
                    WHERE dish.id = ?;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, dishId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Dish dish = null;

                while (resultSet.next()) {
                    if (dish == null) {
                        dish = new Dish();
                        dish.setId(resultSet.getLong("dish_id"));
                        dish.setName(resultSet.getString("dish_name"));
                        dish.setIngredientList(new ArrayList<>());
                    }

                    Ingredient ingredient = new Ingredient();
                    Long ingredientId = resultSet.getLong("id_ingredient");
                    ingredient.setId(ingredientId);
                    ingredient.setName(resultSet.getString("ingredient_name"));

                    // Historique des prix
                    List<IngredientPrice> priceHistory = ingredientPriceDao.findAllForIngredient(ingredientId);
                    ingredient.setPriceHistory(priceHistory);

                    BigDecimal requiredQuantity = resultSet.getBigDecimal("required_quantity");
                    String unit = resultSet.getString("unit");

                    DishIngredient dishIngredient = new DishIngredient(ingredient, requiredQuantity, Unit.valueOf(unit));
                    dish.getIngredientList().add(dishIngredient);
                }

                return dish;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du plat", e);
        }
    }

    @Override
    public boolean deleteOperation(long id) {
        String query =
                """
                    DELETE FROM dish WHERE id = ?;
                """;

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int rowAffected = preparedStatement.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du plat", e);
        }
    }
}
