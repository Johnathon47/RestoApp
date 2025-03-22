package entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class StockMovement {
    private long id;
    private Ingredient ingredient;
    private MovementType movementType;
    private BigDecimal quantity;
    private Unit unit;
    private Timestamp movementDate;
}
