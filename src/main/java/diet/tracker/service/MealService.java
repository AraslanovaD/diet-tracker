package diet.tracker.service;

import diet.tracker.entity.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealService {
    List<Meal> getAll();
    Meal get(int id);
    Meal save(Meal meal);
    void delete(int id);

    List<Meal> getByUserIdAndDate(int id, LocalDate date);

}
