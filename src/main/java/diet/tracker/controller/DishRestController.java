package diet.tracker.controller;

import diet.tracker.entity.Dish;
import diet.tracker.service.DishService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishRestController {
    @Autowired
    private DishService service;

    @GetMapping("")
    public List<Dish> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Dish getById(@PathVariable("id")int id){
        return service.get(id);
    }

    @PostMapping("")
    public ResponseEntity<Dish> add(@RequestBody @Valid Dish dish){
        Dish created = service.save(dish);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<Dish> update(@RequestBody @Valid Dish dish){
        Dish updated = service.save(dish);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        service.get(id);

        service.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
