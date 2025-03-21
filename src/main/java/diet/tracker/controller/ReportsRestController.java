package diet.tracker.controller;


import diet.tracker.dto.MealDTO;
import diet.tracker.dto.UserDTO;
import diet.tracker.entity.Dish;
import diet.tracker.entity.Meal;
import diet.tracker.service.MealService;
import diet.tracker.service.ReportService;
import diet.tracker.service.UserService;
import diet.tracker.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/{id}/reports")
public class ReportsRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private Mapper mapper;

    @GetMapping("")
    public ResponseEntity<Object> getDailyReport(@PathVariable("id") int id, @RequestParam("date") LocalDate date){
        List<Meal> meals = mealService.getByUserIdAndDate(id, date);

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("totalCalories",  reportService.countCalories(meals));
        responseBody.put("mealCount", meals.size());

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/result")
    public ResponseEntity<Object> getDailyResult(@PathVariable("id") int id, @RequestParam("date") LocalDate date){
        List<Meal> meals = mealService.getByUserIdAndDate(id, date);

        double totalCalories = reportService.countCalories(meals);

        UserDTO user = mapper.mapToUserDTO(userService.get(id));

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("result",  reportService.dailyResult(totalCalories, user.getDailyNorm()));

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/history")
    public List<MealDTO> getMealHistory(@PathVariable("id") int id, @RequestParam(name="date",required = false) LocalDate date){
        if(date==null)
            return userService
                .get(id)
                .getMeals()
                .stream()
                .map(mapper::mapToMeatDTO)
                .toList();

        return mealService
                .getByUserIdAndDate(id,date)
                .stream()
                .map(mapper::mapToMeatDTO)
                .toList();
    }
}
