package diet.tracker.service;

import diet.tracker.entity.Meal;

import java.util.List;

public interface ReportService {
    double countCalories(List<Meal> meals);
    String dailyResult(double totalCalories, double dailyCalories);
}
