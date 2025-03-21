package diet.tracker.service;
import diet.tracker.entity.Dish;
import diet.tracker.entity.Meal;
import diet.tracker.entity.User;
import diet.tracker.exception.NoSuchDataException;
import diet.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User get(int id) {
        return repository.findById(id).orElseThrow(()->new NoSuchDataException("There is no user with id="+id));
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public User addMeal(int id, Meal meal) {
        User user = get(id);
        user.getMeals().add(meal);
        return save(user);
    }

    @Override
    public void removeMeal(int id, int mealId) {
        User user = get(id);

        Meal meal = user
                .getMeals()
                .stream()
                .filter(m->m.getId()==mealId)
                .findFirst().orElse(null);

        user.getMeals().remove(meal);

        save(user);
    }




}

