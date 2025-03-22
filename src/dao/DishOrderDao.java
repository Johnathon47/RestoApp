package dao;

import db.DataSource;
import entity.Dish;
import entity.DishOrder;
import entity.Order;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishOrderDao implements CrudOperations<DishOrder> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<DishOrder> saveAll(List<DishOrder> dishOrders) {
        String query = "INSERT INTO dish_order (id, dish_id, quantitytoorder, orderid) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE " +
                "SET dish_id = EXCLUDED.dish_id, " +
                "    quantitytoorder = EXCLUDED.quantitytoorder, " +
                "    orderid = EXCLUDED.orderid";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (DishOrder dishOrder : dishOrders) {
                statement.setLong(1, dishOrder.getId());
                statement.setLong(2, dishOrder.getDish().getId()); // Assurez-vous que DishOrder ait une instance Dish
                statement.setDouble(3, dishOrder.getQuantityToOrder());
                statement.setLong(4, dishOrder.getOrder().getId());
                statement.executeUpdate(); // Exécution de la requête
            }
            return dishOrders;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la commande de plat", e);
        }
    }

    @Override
    public List<DishOrder> getAll(int offset, int limit) {
        String query = "SELECT dish_order.id, dish.name, quantitytoorder, orderid FROM dish_order INNER JOIN dish ON dish_order.dish_id = dish.id GROUP BY dish_order.id, dish.name LIMIT ? OFFSET ?";
        List<DishOrder> dishOrders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DishOrder dishOrder = new DishOrder(
                        resultSet.getLong("id"),
                        (Dish) resultSet.getObject("dish_id"),
                        resultSet.getDouble("quantitytoorder"),
                        (Order) resultSet.getObject("orderid")
                );
                dishOrders.add(dishOrder);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commandes de plats", e);
        }

        return dishOrders;
    }

    @Override
    public DishOrder findById(long E_id) {
        return null;
    }

    @Override
    public boolean deleteOperation(long id) {
        return false;
    }
}
