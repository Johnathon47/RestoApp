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

    public void saveIngredientIfNotExists(Ingredient ingredient) throws SQLException {
        String ingredientQuery = "INSERT INTO ingredient (id, name, unit_price, unit) " +
                "SELECT ?, ?, ?, ? " +
                "WHERE NOT EXISTS (SELECT * FROM ingredient WHERE id = ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ingredientStatement = connection.prepareStatement(ingredientQuery)) {
            ingredientStatement.setInt(1, ingredient.getId());
            ingredientStatement.setString(2, ingredient.getName());
            ingredientStatement.setBigDecimal(3, ingredient.getUnitPrice());
            ingredientStatement.setString(4, ingredient.getUnit().name());
            ingredientStatement.setInt(5, ingredient.getId());
            ingredientStatement.executeUpdate();
        }
    }

    @Override
    public List<Dish> saveAll(List<Dish> dishes) {
        String dishQuery = "INSERT INTO dish (id, name, unit_price) VALUES (?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, unit_price = EXCLUDED.unit_price";

        String dishIngredientQuery = "INSERT INTO dish_ingredient (id_dish, id_ingredient, required_quantity, unit) " +
                "VALUES (?, ?, ?, ?::unit) " +
                "ON CONFLICT (id_dish, id_ingredient) DO UPDATE SET required_quantity = EXCLUDED.required_quantity, unit = EXCLUDED.unit";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement dishStatement = connection.prepareStatement(dishQuery);
             PreparedStatement dishIngredientStatement = connection.prepareStatement(dishIngredientQuery)) {

            for (Dish dish : dishes) {
                // Insertion du plat dans la table dish
                dishStatement.setInt(1, dish.getId());
                dishStatement.setString(2, dish.getName());
                dishStatement.setBigDecimal(3, dish.getUnitPrice());
                dishStatement.executeUpdate();

                // Insertion des ingrédients associés à ce plat dans dish_ingredient
                for (DishIngredient dishIngredient : dish.getIngredientList()) {
                    saveIngredientIfNotExists(dishIngredient.getIngredient());
                    dishIngredientStatement.setInt(1, dish.getId()); // Plat id
                    dishIngredientStatement.setInt(2, dishIngredient.getIngredient().getId()); // Ingrédient id
                    dishIngredientStatement.setBigDecimal(3, dishIngredient.getRequiredQuantity()); // quantité requise
                    dishIngredientStatement.setString(4, String.valueOf(dishIngredient.getUnit())); // Unité de l'ingrédient
                    dishIngredientStatement.executeUpdate();
                }
            }

            return dishes; // Retourner la liste des plats après les avoir enregistrés
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement des plats", e);
        }
    }



    @Override
    public List<Dish> getAll(int offset, int limit) {
        List<Dish> dishes = new ArrayList<>();
        Map<Integer, Dish> dishMap = new HashMap<>(); // Pour éviter les doublons

        String sql = "SELECT dish.id, dish.name, dish.unit_price, ingredient.name AS ingredientName, \n" +
                "       dish_ingredient.required_quantity, dish_ingredient.unit \n" +
                "FROM (SELECT * FROM dish ORDER BY id LIMIT ? OFFSET ?) AS dish \n" +
                "INNER JOIN dish_ingredient ON dish.id = dish_ingredient.id_dish \n" +
                "INNER JOIN ingredient ON dish_ingredient.id_ingredient = ingredient.id;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, (limit ));
            preparedStatement.setInt(2, (limit * (offset - 1)));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int dishId = resultSet.getInt("id");

                    // Vérifie si le plat existe déjà dans la map
                    Dish dish = dishMap.get(dishId);
                    if (dish == null) {
                        dish = new Dish();
                        dish.setId(dishId);
                        dish.setName(resultSet.getString("name"));
                        dish.setUnitPrice(resultSet.getBigDecimal("unit_price"));
                        dish.setIngredientList(new ArrayList<>()); // Assure que la liste d'ingrédients n'est jamais null
                        dishMap.put(dishId, dish);
                        dishes.add(dish);
                    }

                    // Ajout des ingrédients s'ils existent
                    String ingredientName = ingredientName = resultSet.getString("ingredientName");
                    if (ingredientName != null) {
                        BigDecimal requiredQuantity = resultSet.getBigDecimal("required_quantity");
                        String unit = resultSet.getString("unit");

                        DishIngredient dishIngredient = new DishIngredient(ingredientName, requiredQuantity, Unit.valueOf(unit));
                        dish.getIngredientList().add(dishIngredient);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishes;
    }


    @Override
    public Dish findById(int dishId) {
        String sql = "SELECT dish.id, dish.name, dish.unit_price, ingredient.name AS ingredientName, \n" +
                "       dish_ingredient.required_quantity, dish_ingredient.unit \n" +
                "FROM dish \n" +
                "LEFT JOIN dish_ingredient ON dish.id = dish_ingredient.id_dish \n" +
                "LEFT JOIN ingredient ON dish_ingredient.id_ingredient = ingredient.id \n" +
                "WHERE dish.id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, dishId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Dish dish = null;

                while (resultSet.next()) {
                    if (dish == null) {
                        dish = new Dish();
                        dish.setId(resultSet.getInt("id"));
                        dish.setName(resultSet.getString("name"));
                        dish.setUnitPrice(resultSet.getBigDecimal("unit_price"));
                        dish.setIngredientList(new ArrayList<>()); // Initialisation de la liste
                    }

                    // Vérifier si l'ingrédient existe avant d'ajouter
                    String ingredientName = resultSet.getString("ingredientName");
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
    public boolean deleteOperation(int id) {
        String query = "DELETE FROM dish WHERE id = ?";

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowAffected = preparedStatement.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
