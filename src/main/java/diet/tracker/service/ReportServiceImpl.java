package diet.tracker.service;

import diet.tracker.entity.Dish;
import diet.tracker.entity.Meal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{
    @Override
    public double countCalories(List<Meal> meals){
        return meals.stream().mapToDouble(
                meal -> meal.getDishes().stream().mapToDouble(Dish::getCalories).sum()
        ).sum();
    }

    @Override
    public String dailyResult(double totalCalories, double dailyCalories) {
        if(totalCalories == dailyCalories) return "daily norm is met";
        if(totalCalories < dailyCalories ) return "less than the daily norm";
        return "more than the daily norm";
    }

}

