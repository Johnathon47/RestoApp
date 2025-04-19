package dao;

import db.DataSource;
import entity.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao implements CrudOperations<Ingredient> {
    private final DataSource dataSource = new DataSource();
    private final IngredientPriceDao priceDao = new IngredientPriceDao(); // DAO pour l'historique des prix

    public List<Ingredient> saveAll(List<Ingredient> ingredients) {
        String query = "INSERT INTO ingredient (id, name) " +
                "VALUES (?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (Ingredient ingredient : ingredients) {
                statement.setLong(1, ingredient.getId());
                statement.setString(2, ingredient.getName());
                statement.executeUpdate();
            }

            return ingredients;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'ingrédient", e);
        }
    }

    @Override
    public List<Ingredient> getAll(int offset, int limit) {
        String sql = "SELECT i.id, i.name FROM ingredient i ORDER BY i.name LIMIT ? OFFSET ?;";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, (limit * (offset - 1)));

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ingredient> ingredients = new ArrayList<>();

            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                long id = resultSet.getLong("id");

                ingredient.setId(id);
                ingredient.setName(resultSet.getString("name"));

                // 🆕 Charger l'historique des prix
                ingredient.setPriceHistory(priceDao.findAllForIngredient(id));

                ingredients.add(ingredient);
            }

            return ingredients;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient findById(long id_ingredient) {
        String query = "SELECT i.id, i.name FROM ingredient i WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id_ingredient);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getLong("id"));
                ingredient.setName(resultSet.getString("name"));

                // 🆕 Charger l'historique des prix
                ingredient.setPriceHistory(priceDao.findAllForIngredient(id_ingredient));

                return ingredient;
            } else {
                return null; // Aucun ingrédient trouvé
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteOperation(long id) {
        String query = "DELETE FROM ingredient WHERE id = ?;";

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
