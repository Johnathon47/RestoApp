package dao;

import db.DataSource;
import entity.Dish;
import entity.DishAvailability;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishAvailabailityDao {
    private final DataSource dataSource = new DataSource();

    public List<DishAvailability> findAll() {
        List<DishAvailability> availabilities = new ArrayList<>();
        String requestSql = """
                                SELECT dish_id, dish.name AS name, dish.unit_price AS unit_price, availability FROM dish_availability
                                INNER JOIN dish ON dish_availability.dish_id = dish.id
                                GROUP BY dish_id, dish.name, dish.unit_price, availability;
                            """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(requestSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    DishAvailability availability = new DishAvailability();
                    Dish dish = new Dish();
                    dish.setId(resultSet.getLong("dish_id"));
                    dish.setName(resultSet.getString("name"));
                    dish.setIngredientList(new ArrayList<>());


                    availability.setDish(dish);
                    availability.setAvailability(resultSet.getDouble("availability"));
                    availabilities.add(availability);
                }
            return availabilities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
