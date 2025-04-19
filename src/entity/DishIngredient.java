package entity;

import java.math.BigDecimal;
import java.util.Objects;

public class DishIngredient {
    private Ingredient ingredient;
    private BigDecimal requiredQuantity;
    private Unit unit;

    public DishIngredient() {}

    public DishIngredient(Ingredient ingredient, BigDecimal requiredQuality, Unit unit) {
        this.ingredient = ingredient;
        this.requiredQuantity = requiredQuality;
        this.unit = unit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
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
        return "DishIngredient{" +
                "name=" + ingredient.getName() +
                ", requiredQuantity=" + requiredQuantity +
                ", unit=" + unit + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DishIngredient that = (DishIngredient) o;
        return Objects.equals(ingredient, that.ingredient) &&
                Objects.equals(requiredQuantity, that.requiredQuantity) &&
                unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ingredient, requiredQuantity, unit);
    }
}
