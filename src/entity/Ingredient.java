package entity;

import java.math.BigDecimal;
import java.util.List;

public class Ingredient {
    private Long id;
    protected String name;
    private List<IngredientPrice> priceHistory;

    public Ingredient() {
    }

    public Ingredient(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient(Long id, String name, List<IngredientPrice> priceHistory) {
        this.id = id;
        this.name = name;
        this.priceHistory = priceHistory;
    }

    public BigDecimal getCurrentPrice() {
        IngredientPrice latest = getLatestPrice();
        return latest != null ? latest.getPrice() : BigDecimal.ZERO;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientPrice> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<IngredientPrice> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public IngredientPrice getLatestPrice() {
        if (priceHistory == null || priceHistory.isEmpty()) return null;

        return priceHistory.stream()
                .max((p1, p2) -> p1.getUpdateDateTime().compareTo(p2.getUpdateDateTime()))
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceHistory=" + priceHistory +
                '}'+"\n";
    }
}
