package entity;

import java.math.BigDecimal;
import java.util.Objects;

public class DishIngredient extends Ingredient {
    private int idDish;
    private int idIngredient;
    private BigDecimal requiredQuantity;
    private Unit unit;

    public DishIngredient() {}

    public DishIngredient(String name, BigDecimal requiredQuality, Unit unit) {
        this.name = name;
        this.requiredQuantity = requiredQuality;
        this.unit = unit;
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "\n      DishIngredient{" +
                "\n         name=" + name +
                ",\n        requiredQuantity=" + requiredQuantity +
                ",\n        unit='" + unit + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DishIngredient that = (DishIngredient) o;
        return idDish == that.idDish && idIngredient == that.idIngredient && requiredQuantity == that.requiredQuantity && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDish, idIngredient, requiredQuantity, unit);
    }
}
