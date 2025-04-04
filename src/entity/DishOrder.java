package entity;

public class DishOrder {
    private long id;
    private Dish dish;
    private Double quantityToOrder;
    private Double price;
    private Order order;

    public DishOrder(long id, Dish dish, Double quantityToOrder,  Double price, Order order) {
        this.id = id;
        this.dish = dish;
        this.quantityToOrder = quantityToOrder;
        this.price = price;
        this.order = order;
    }

    public DishOrder() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Double getQuantityToOrder() {
        return quantityToOrder;
    }

    public void setQuantityToOrder(Double quantityToOrder) {
        this.quantityToOrder = quantityToOrder;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "DishOrder{" +
                ", name='" + dish.getName() + '\'' +
                ", quantityToOrder=" + quantityToOrder +
                ", order=" + order.getId() +
                '}';
    }
}
