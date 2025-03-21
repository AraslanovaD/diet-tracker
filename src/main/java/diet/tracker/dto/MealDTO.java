package diet.tracker.dto;

import diet.tracker.entity.Dish;

import java.time.LocalDate;
import java.util.List;

public class MealDTO {

    private LocalDate date;

    private List<Dish> dishes;

    public MealDTO() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
