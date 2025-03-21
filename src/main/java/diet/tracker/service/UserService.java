package diet.tracker.service;

import diet.tracker.entity.Meal;
import diet.tracker.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User get(int id);
    User save(User user);
    void delete(int id);

    User addMeal(int id, Meal meal);
    void removeMeal(int id, int mealId);

}
