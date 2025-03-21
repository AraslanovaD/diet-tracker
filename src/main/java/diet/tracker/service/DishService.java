package diet.tracker.service;

import diet.tracker.entity.Dish;

import java.util.List;

public interface DishService {
    List<Dish> getAll();
    Dish get(int id);
    Dish save(Dish dish);
    void delete(int id);
}
