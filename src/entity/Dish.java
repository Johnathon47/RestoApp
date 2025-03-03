package entity;

import java.math.BigDecimal;
import java.util.List;

public class Dish {
    private int id;
    private String name;
    private BigDecimal unitPrice;
    private List<DishIngredient> ingredientList;

    public Dish (int id, String name, BigDecimal unitPrice, List<DishIngredient> listIngredient){
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.ingredientList = listIngredient;
    };
    public Dish () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<DishIngredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<DishIngredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public String toString() {
        return "Dish{" +
                " \n  id=" + id +
                ",\n name='" + name + '\'' +
                ",\n unitPrice=" + unitPrice +
                ",\n ingredientList=" + ingredientList +
                '}';
    }
}
