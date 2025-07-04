package entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private long id;
    private TableNumber tableNumber;
    private Double amountPaid;
    private Double amountDue;
    private Instant customerArrivalDatetime;
    private List<DishOrder> dishOrders;

    public Order(Instant customerArrivalDatetime, Double amountDue, Double amountPaid, TableNumber tableNumber, long id) {
        this.customerArrivalDatetime = customerArrivalDatetime;
        this.amountDue = amountDue;
        this.amountPaid = amountPaid;
        this.tableNumber = tableNumber;
        this.id = id;
        this.dishOrders = new ArrayList<>();  // Initialise la liste des DishOrder
    }

    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(TableNumber tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public Instant getCustomerArrivalDatetime() {
        return customerArrivalDatetime;
    }

    public void setCustomerArrivalDatetime(Instant customerArrivalDatetime) {
        this.customerArrivalDatetime = customerArrivalDatetime;
    }

    // Liste des DishOrder
    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }

    // Ajout d'un DishOrder et gestion de la relation bidirectionnelle
    public void addDishOrder(DishOrder dishOrder) {
        if (this.dishOrders == null) {
            this.dishOrders = new ArrayList<>();
        }
        this.dishOrders.add(dishOrder);
        dishOrder.setOrder(this);  // Relation bidirectionnelle : lier le DishOrder à cet Order
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", tableNumber=" + tableNumber +
               ", amountPaid=" + amountPaid +
               ", amountDue=" + amountDue +
               ", customerArrivalDatetime=" + customerArrivalDatetime +
               ", dishOrders=" + dishOrders +
               '}';
    }
}
