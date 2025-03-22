package dao;

import db.DataSource;
import entity.*;
import entity.DishIngredient;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishDao implements CrudOperations<Dish>{
    private final DataSource dataSource = new DataSource();

    private void updateUnitPrice(Dish dish, Connection connection) throws SQLException {
        String updateQuery = "UPDATE dish SET unit_price = ? WHERE id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            BigDecimal calculatedPrice = dish.getIngredientCost();
            updateStatement.setBigDecimal(1, calculatedPrice);
            updateStatement.setLong(2, dish.getId());
            updateStatement.executeUpdate();
        }
    }

    @Override
    public List<Dish> saveAll(List<Dish> dishes) {
        String dishQuery = "INSERT INTO dish (id, name) VALUES (?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name";
        String dishIngredientQuery = "INSERT INTO dish_ingredient (id_dish, id_ingredient, required_quantity, unit) " +
                "VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING";

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

                // Mise à jour du prix après insertion
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
        Map<Integer, Dish> dishMap = new HashMap<>();

        String sql = "SELECT dish.id, dish.name, dish_ingredient.required_quantity, dish_ingredient.unit, " +
                "ingredient.name AS ingredientName, ingredient.unit_price " +
                "FROM dish " +
                "INNER JOIN dish_ingredient ON dish.id = dish_ingredient.id_dish " +
                "INNER JOIN ingredient ON dish_ingredient.id_ingredient = ingredient.id " +
                "ORDER BY dish.id LIMIT ? OFFSET ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, (offset - 1) * limit);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int dishId = resultSet.getInt("id");
                    Dish dish = dishMap.get(dishId);
                    if (dish == null) {
                        dish = new Dish();
                        dish.setId(dishId);
                        dish.setName(resultSet.getString("name"));
                        dish.setIngredientList(new ArrayList<>());
                        dishMap.put(dishId, dish);
                        dishes.add(dish);
                    }

                    // Ajouter l'ingrédient au plat
                    String ingredientName = resultSet.getString("ingredientName");
                    BigDecimal requiredQuantity = resultSet.getBigDecimal("required_quantity");
                    String unit = resultSet.getString("unit");
                    BigDecimal unitPrice = resultSet.getBigDecimal("unit_price");

                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingredientName);
                    ingredient.setUnitPrice(unitPrice);  // Set the unit price from database
                    ingredient.setUnit(Unit.valueOf(unit));

                    DishIngredient dishIngredient = new DishIngredient(ingredient, requiredQuantity, Unit.valueOf(unit));
                    dish.getIngredientList().add(dishIngredient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching dishes", e);
        }

        return dishes;
    }


    @Override
    public Dish findById(long dishId) {
        String sql = "SELECT dish.id, dish.name, dish.unit_price, ingredient.name AS ingredientName, \n" +
                "       dish_ingredient.required_quantity, dish_ingredient.unit \n" +
                "FROM dish \n" +
                "LEFT JOIN dish_ingredient ON dish.id = dish_ingredient.id_dish \n" +
                "LEFT JOIN ingredient ON dish_ingredient.id_ingredient = ingredient.id \n" +
                "WHERE dish.id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, dishId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Dish dish = null;

                while (resultSet.next()) {
                    if (dish == null) {
                        dish = new Dish();
                        dish.setId(resultSet.getInt("id"));
                        dish.setName(resultSet.getString("name"));

                        dish.setIngredientList(new ArrayList<>()); // Initialisation de la liste
                    }

                    // Vérifier si l'ingrédient existe avant d'ajouter
                    Ingredient ingredientName = (Ingredient) resultSet.getObject("ingredientName");
                    if (ingredientName != null) {
                        BigDecimal requiredQuantity = resultSet.getBigDecimal("required_quantity");
                        String unit = resultSet.getString("unit");

                        DishIngredient dishIngredient = new DishIngredient(ingredientName, requiredQuantity, Unit.valueOf(unit));
                        dish.getIngredientList().add(dishIngredient);
                    }
                }
                return dish; // Retourne null si aucun plat n'est trouvé
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public boolean deleteOperation(long id) {
        String query = "DELETE FROM dish WHERE id = ?";

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int rowAffected = preparedStatement.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
