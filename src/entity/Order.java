package entity;

import java.time.Instant;
import java.util.List;

public class Order {
    private long id;
    private  TableNumber tableNumber;
    private Double amountPaid;
    private Double amountDue;
    private Instant customerArrivalDatetime;
    private List<DishOrder> dishOrders;

    /*public List<DishOrder> addDishOrders(List<DishOrder> dishOrders) {

    }

    public OrderPaymentStatus getPaymentStatus(){
    }

    public Double getTotalPrice(){
    }
     */

    public Order(Instant customerArrivalDatetime, Double amountDue, Double amountPaid, TableNumber tableNumber, long id) {
        this.customerArrivalDatetime = customerArrivalDatetime;
        this.amountDue = amountDue;
        this.amountPaid = amountPaid;
        this.tableNumber = tableNumber;
        this.id = id;
    }

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

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
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
