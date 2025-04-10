package entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class StockMovement {
    private long id;
    private Ingredient ingredient;
    private MovementType movementType;
    private BigDecimal quantity;
    private Unit unit;
    private Timestamp movementDate;

    public StockMovement() {
    }

    public StockMovement(long id, Ingredient ingredient, MovementType movementType, BigDecimal quantity, Unit unit, Timestamp movementDate) {
        this.id = id;
        this.ingredient = ingredient;
        this.movementType = movementType;
        this.quantity = quantity;
        this.unit = unit;
        this.movementDate = movementDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Timestamp getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Timestamp movementDate) {
        this.movementDate = movementDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StockMovement that = (StockMovement) o;
        return id == that.id && Objects.equals(ingredient, that.ingredient) && movementType == that.movementType && Objects.equals(quantity, that.quantity) && unit == that.unit && Objects.equals(movementDate, that.movementDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredient, movementType, quantity, unit, movementDate);
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id=" + id +
                ", ingredient=" + ingredient.getName() +
                ", movementType=" + movementType +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", movementDate=" + movementDate +
                '}';
    }
}
