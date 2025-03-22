package dao;

import db.DataSource;
import entity.Ingredient;
import entity.Unit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao implements CrudOperations<Ingredient>{
    private final DataSource dataSource = new DataSource();


    public List<Ingredient> saveAll(List<Ingredient> ingredients) {
        String query = "INSERT INTO ingredient (id, name, unit_price, unit, update_datetime) " +
                "VALUES (?, ?, ?, ?::unit, ?) " +
                "ON CONFLICT (id) DO UPDATE " +
                "SET name = EXCLUDED.name, " +
                "    unit_price = EXCLUDED.unit_price, " +
                "    unit = EXCLUDED.unit, " +
                "    update_datetime = EXCLUDED.update_datetime";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (Ingredient ingredient : ingredients) {
                statement.setLong(1, ingredient.getId());
                statement.setString(2, ingredient.getName());
                statement.setBigDecimal(3, ingredient.getUnitPrice());
                statement.setString(4, ingredient.getUnit().name()); // Attention si Unit est ENUM
                statement.setTimestamp(5, ingredient.getUpdateDateTime());

                statement.executeUpdate(); // Exécute un par un, pas de batch !
            }

            return ingredients; // Retourne la liste après sauvegarde

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'ingrédient", e);
        }
    }



    @Override
    public List<Ingredient> getAll(int offset, int limit) {
        String sql = "SELECT i.id, i.name, i.unit_price, i.unit, i.update_datetime FROM ingredient i ORDER BY i.id LIMIT ? OFFSET ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, (limit * (offset - 1)));
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ingredient> ingredients = new ArrayList<>();
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setUnitPrice(resultSet.getBigDecimal("unit_price"));
                ingredient.setUnit(Unit.valueOf(resultSet.getString("unit")));
                ingredient.setUpdateDateTime(resultSet.getTimestamp("update_datetime"));
                ingredients.add(ingredient);
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient findById(int id_ingredient) {
        String query = "SELECT i.id, i.name, i.unit_price, i.unit, i.update_datetime FROM ingredient i WHERE id = ?;";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,id_ingredient);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Ingredient ingredient = new Ingredient();
                while (resultSet.next()) {
                    ingredient.setId(resultSet.getInt("id"));
                    ingredient.setName(resultSet.getString("name"));
                    ingredient.setUnitPrice(resultSet.getBigDecimal("unit_price"));
                    ingredient.setUnit(Unit.valueOf(resultSet.getString("unit")));
                    ingredient.setUpdateDateTime(resultSet.getTimestamp("update_datetime"));
                }
                return ingredient;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteOperation(int id) {
        String query = "DELETE FROM ingredient WHERE id = ?;";

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            // Vérifie si la ligne a été supprimé
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
