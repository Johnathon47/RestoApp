package entity;

import dao.DishAvailabailityDao;

import java.util.List;
import java.util.Objects;

public class DishAvailability {
    private Dish dish;
    private Double availability;

    public DishAvailability () {
    }

    public List<DishAvailability> getAllDishAvailability() {
        DishAvailabailityDao dishAvailabailityDao = new DishAvailabailityDao();
        return dishAvailabailityDao.findAll();
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(Double availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "DishAvailability\n{" +
                "dish=" + dish +
                ", availability=" + availability +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DishAvailability that = (DishAvailability) o;
        return Objects.equals(dish, that.dish) && Objects.equals(availability, that.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish, availability);
    }
}
