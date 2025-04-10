package dao;

import db.DataSource;
import entity.Ingredient;
import entity.MovementType;
import entity.StockMovement;
import entity.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDao{
    private final DataSource dataSource = new DataSource();

    public List<StockMovement> stockMovementList () {
        List<StockMovement> movementList = new ArrayList<>();
        String query =
                """
                    SELECT stock_movement.id, name,stock_movement.movement_type, stock_movement.quantity, stock_movement.unit, stock_movement.movement_date 
                    FROM ingredient INNER JOIN stock_movement ON ingredient.id = stock_movement.ingredient_id;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement prstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = prstmt.executeQuery();
            while (resultSet.next()) {
                StockMovement stockMovement = new StockMovement();
                stockMovement.setId(resultSet.getLong("id"));
                stockMovement.setMovementType(MovementType.valueOf(resultSet.getString("movement_type")));
                stockMovement.setQuantity(resultSet.getBigDecimal("quantity"));
                stockMovement.setUnit(Unit.valueOf(resultSet.getString("unit")));
                stockMovement.setMovementDate(resultSet.getTimestamp("movement_date"));

                Ingredient ingredient = new Ingredient();
                ingredient.setName(resultSet.getString("name"));

                stockMovement.setIngredient(ingredient);

                movementList.add(stockMovement);
            }
            return movementList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
