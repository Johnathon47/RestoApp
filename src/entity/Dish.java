package entity;

import java.math.BigDecimal;
import java.util.List;

public class Dish {
    private int id;
    private String name;
    private BigDecimal unitPrice;
    private List<Ingredient> ingredientList;

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

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
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
