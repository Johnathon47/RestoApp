package entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Ingredient {
    private int id;
    protected String name;
    private BigDecimal unitPrice;
    private Unit unit;
    private Timestamp updateDateTime;

    public Ingredient(int id, String name, BigDecimal unitPrice, Unit unit, Timestamp updateDateTime) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.updateDateTime = updateDateTime;
    }

    public Ingredient (String name) {
        this.name = name;
    }

    public Ingredient() {}

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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "\n  id=" + id +
                ",\n name='" + name + '\'' +
                ",\n unitPrice=" + unitPrice +
                ",\n unit=" + unit +
                ",\n updateDateTime=" + updateDateTime +
                '}'+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(name, that.name) && unit == that.unit && Objects.equals(updateDateTime, that.updateDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unitPrice, unit, updateDateTime);
    }
}
