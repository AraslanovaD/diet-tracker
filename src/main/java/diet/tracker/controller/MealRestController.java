package diet.tracker.controller;

import diet.tracker.dto.MealDTO;
import diet.tracker.entity.Meal;
import diet.tracker.service.MealService;
import diet.tracker.utils.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealRestController {
    @Autowired
    private MealService service;

    @Autowired
    private Mapper mapper;

    @GetMapping("")
    public List<MealDTO> getAll(){
        return service
                .getAll()
                .stream()
                .map(mapper::mapToMeatDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public Meal getById(@PathVariable("id")int id){
        return service.get(id);
    }

    @PostMapping("")
    public ResponseEntity<Meal> add(@RequestBody @Valid Meal meal){
        Meal created = service.save(meal);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<Meal> update(@RequestBody @Valid Meal meal){
        Meal updated = service.save(meal);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        service.get(id);

        service.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
