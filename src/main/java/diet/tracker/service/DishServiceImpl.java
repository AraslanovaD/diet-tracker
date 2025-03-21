package diet.tracker.service;

import diet.tracker.entity.Dish;
import diet.tracker.exception.NoSuchDataException;
import diet.tracker.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService{
    @Autowired
    private DishRepository repository;

    @Override
    public List<Dish> getAll() {
        return repository.findAll();
    }

    @Override
    public Dish get(int id) {
        return repository.findById(id).orElseThrow(()->new NoSuchDataException("There is no dish with id="+id));
    }

    @Override
    public Dish save(Dish dish) {
        return repository.save(dish);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
