package diet.tracker.repository;

import diet.tracker.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal,Integer> {
    List<Meal> getByUserIdAndDate(int id, LocalDate date);
}
