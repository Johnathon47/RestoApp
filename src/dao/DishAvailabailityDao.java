package dao;

import db.DataSource;
import entity.Dish;
import entity.DishAvailability;

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
        String requestSql = "SELECT dish.name AS name, availability FROM dish_availability\n"+
                            "INNER JOIN dish ON dish_availability.dish_id = dish.id\n"+
                            "GROUP BY dish.name, availability";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(requestSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    DishAvailability availability = new DishAvailability();
                    availability.setName(resultSet.getString("name"));
                    availability.setAvailability(resultSet.getDouble("availability"));
                    availabilities.add(availability);
                }
            return availabilities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
