package diet.tracker.service;

import diet.tracker.entity.Dish;
import diet.tracker.entity.Meal;
import diet.tracker.exception.NoSuchDataException;
import diet.tracker.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealServiceImpl implements MealService{
    @Autowired
    private MealRepository repository;

    @Override
    public List<Meal> getAll() {
        return repository.findAll();
    }

    @Override
    public Meal get(int id) {
        return repository.findById(id).orElseThrow(()->new NoSuchDataException("There is no meal with id="+id));
    }

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<Meal> getByUserIdAndDate(int id, LocalDate date) {
        return repository.getByUserIdAndDate(id,date);
    }

}

