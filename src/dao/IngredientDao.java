package dao;

import db.DataSource;
import entity.Ingredient;
import entity.Unit;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao implements CrudOperations<Ingredient>{
    private final DataSource dataSource = new DataSource();


    @Override
    public List<Ingredient> getAll(int offset, int limit) {
        String sql = "SELECT i.id, i.name, i.unit_price, i.unit, i.update_datetime FROM ingredient i ORDER BY i.id LIMIT ? OFFSET ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, (limit * (offset -1)));
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ingredient> ingredients = new ArrayList<>();
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setUnitPrice(BigDecimal.valueOf(resultSet.getInt("unit_price")));
                ingredient.setUnit(Unit.valueOf(resultSet.getString("unit")));
                ingredient.setUpdateDateTime(resultSet.getDate("update_datetime"));
                ingredients.add(ingredient);
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient findById(List<Ingredient> list, int E_id) {
        return null;
    }
}
