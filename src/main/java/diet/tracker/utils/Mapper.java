package diet.tracker.utils;

import diet.tracker.dto.MealDTO;
import diet.tracker.dto.UserDTO;
import diet.tracker.entity.Meal;
import diet.tracker.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public UserDTO mapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        userDTO.setWeight(user.getWeight());
        userDTO.setHeight(user.getHeight());
        userDTO.setGender(user.getGender());
        userDTO.setGoal(user.getGoal());
        userDTO.setDailyNorm();
        return userDTO;
    }

    public MealDTO mapToMeatDTO(Meal meal){
        MealDTO mealDTO = new MealDTO();
        mealDTO.setId(meal.getId());
        mealDTO.setDate(meal.getDate());
        mealDTO.setDishes(meal.getDishes());
        return mealDTO;
    }
}
