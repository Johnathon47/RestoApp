package dao;


import db.DataSource;
import entity.*;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDao implements CrudOperations<Order> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<Order> saveAll(List<Order> orders) {
        String insertOrderQuery = """
        INSERT INTO "order" (id, tablenumber, amountpaid, amountdue, customerarrivaldatetime)
        VALUES (?, ?::table_number, ?, ?, ?)
    """;

        String insertDishOrderQuery = """
        INSERT INTO dish_order (id, dish_id, quantityToOrder, price, orderId)
        VALUES (?, ?, ?, ?, ?)
    """;

        try (Connection connection = dataSource.getConnection()) {
            for (Order order : orders) {
                try (PreparedStatement insertOrderStmt = connection.prepareStatement(insertOrderQuery);
                     PreparedStatement insertDishOrderStmt = connection.prepareStatement(insertDishOrderQuery)) {

                    // Insert order
                    insertOrderStmt.setLong(1, order.getId());
                    insertOrderStmt.setString(2, order.getTableNumber().name());
                    insertOrderStmt.setDouble(3, order.getAmountPaid());
                    insertOrderStmt.setDouble(4, order.getAmountDue());
                    insertOrderStmt.setTimestamp(5, Timestamp.from(order.getCustomerArrivalDatetime()));
                    insertOrderStmt.executeUpdate();

                    // Insert dish_orders only if the list is not null
                    if (order.getDishOrders() != null) {
                        for (DishOrder dishOrder : order.getDishOrders()) {
                            insertDishOrderStmt.setLong(1, dishOrder.getId());
                            insertDishOrderStmt.setLong(2, dishOrder.getDish().getId());
                            insertDishOrderStmt.setDouble(3, dishOrder.getQuantityToOrder());
                            insertDishOrderStmt.setDouble(4, dishOrder.getPrice());
                            insertDishOrderStmt.setLong(5, order.getId());
                            insertDishOrderStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }


    @Override
    public List<Order> getAll(int offset, int limit) {
        List<Order> orders = new ArrayList<>();
        String getOrdersQuery = """
        SELECT orderid, "order".tablenumber, dish.name, quantitytoorder, price, 
               "order".amountpaid, "order".amountdue, "order".customerarrivaldatetime
        FROM dish_order
        INNER JOIN dish ON dish_order.dish_id = dish.id
        INNER JOIN "order" ON dish_order.orderid = "order".id
        ORDER BY orderid
        OFFSET ? LIMIT ?
    """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(getOrdersQuery)) {

            stmt.setInt(1, offset);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();

            Map<Long, Order> orderMap = new HashMap<>();

            while (rs.next()) {
                Long orderId = rs.getLong("orderid");

                // Si l'ordre n'est pas encore dans la liste, on le crée
                if (!orderMap.containsKey(orderId)) {
                    Order order = new Order(
                            rs.getTimestamp("customerarrivaldatetime").toInstant(),
                            rs.getDouble("amountdue"),
                            rs.getDouble("amountpaid"),
                            TableNumber.valueOf(rs.getString("tablenumber")),
                            orderId
                    );
                    orderMap.put(orderId, order);
                }

                // Récupère l'order existant à partir du map
                Order order = orderMap.get(orderId);

                // Crée un DishOrder et l'ajoute à l'ordre
                Dish dish = new Dish(); // Suppose que tu as une classe Dish
                dish.setName(rs.getString("name"));

                DishOrder dishOrder = new DishOrder();
                dishOrder.setDish(dish);
                dishOrder.setQuantityToOrder(Double.valueOf(rs.getInt("quantitytoorder")));
                dishOrder.setPrice(rs.getDouble("price"));

                // Ajoute le dishOrder à l'ordre
                if (order.getDishOrders() == null) {
                    order.setDishOrders(new ArrayList<>());
                }
                order.getDishOrders().add(dishOrder);
            }

            // Convertit la Map en une liste pour les orders
            orders.addAll(orderMap.values());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }


    @Override
    public Order findById(long orderId) {
        String query =
                    """
                        SELECT * FROM "order" WHERE id = ?
                    """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Order order = new Order(
                        rs.getTimestamp("customerArrivalDatetime").toInstant(),
                        rs.getDouble("amountDue"),
                        rs.getDouble("amountPaid"),
                        TableNumber.valueOf(rs.getString("tableNumber")),
                        rs.getLong("id")
                );
                order.setDishOrders(getDishOrdersByOrderId(orderId, connection));
                return order;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteOperation(long id) {
        String deleteDishOrders = "DELETE FROM dish_order WHERE orderId = ?";
        String deleteOrder = "DELETE FROM \"order\" WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteDishStmt = connection.prepareStatement(deleteDishOrders);
             PreparedStatement deleteOrderStmt = connection.prepareStatement(deleteOrder)) {

            // Supprimer les dish_order associés
            deleteDishStmt.setLong(1, id);
            deleteDishStmt.executeUpdate();

            // Supprimer la commande
            deleteOrderStmt.setLong(1, id);
            int affectedRows = deleteOrderStmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<DishOrder> getDishOrdersByOrderId(long orderId, Connection connection) throws SQLException {
        List<DishOrder> dishOrders = new ArrayList<>();
        String query = """
            SELECT d_o.*, d.name, d.id AS dish_id
            FROM dish_order d_o
            JOIN dish d ON d.id = d_o.dish_id
            WHERE orderId = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getLong("dish_id"));
                dish.setName(rs.getString("name"));

                DishOrder dishOrder = new DishOrder(
                        rs.getLong("id"),
                        dish,
                        rs.getDouble("quantityToOrder"),
                        rs.getDouble("price"),
                        null // L'objet Order peut être réinjecté après si besoin
                );
                dishOrders.add(dishOrder);
            }
        }

        return dishOrders;
    }
}
