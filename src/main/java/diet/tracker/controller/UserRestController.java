package diet.tracker.controller;

import diet.tracker.dto.UserDTO;
import diet.tracker.entity.Meal;
import diet.tracker.entity.User;
import diet.tracker.service.MealService;
import diet.tracker.service.UserService;
import diet.tracker.utils.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    private MealService mealService;

    @Autowired
    private UserService service;

    @Autowired
    private Mapper mapper;

    @GetMapping("")
    public List<UserDTO> getAllUsers(){
        return service
                .getAll()
                .stream()
                .map(mapper::mapToUserDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id")int id){
        return mapper.mapToUserDTO(service.get(id));
    }

    @PostMapping("")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user){
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/meals")
    public ResponseEntity<User> addMeal(@PathVariable("id") int id, @RequestBody @Valid Meal meal){
        User created = service.addMeal(id, meal);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user){
        return new ResponseEntity<>(service.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id){
        service.get(id);

        service.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/meals/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable("id") int id, @PathVariable("mealId") int mealId){
        service.get(id);

        service.removeMeal(id, mealId);

        mealService.delete(mealId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
