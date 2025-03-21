package diet.tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import diet.tracker.dto.UserDTO;
import diet.tracker.entity.*;
import diet.tracker.exception.NoSuchDataException;
import diet.tracker.service.MealService;
import diet.tracker.service.ReportService;
import diet.tracker.service.UserService;
import diet.tracker.utils.Mapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
class ReportsTests {

	private static final String PATH = "/api/users/1/reports";

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MealService mealService;

	@MockitoBean
	private ReportService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Mapper mapper;

	@Test
	void testGetDailyReport() throws Exception{
		User user  = new User("name1", "email1@mail.ru", 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE);

		String date = "2025-10-12";
		String request = PATH+"?date="+date;

		Dish dish1 = new Dish("name1", 800, 1,1,1);
		Dish dish2 = new Dish("name2", 852, 1,1,1);
		List<Dish> dishes = List.of(dish1,dish2);

		Meal meal = new Meal(LocalDate.parse(date));
		meal.setDishes(dishes);
		meal.setUser(user);

		List<Meal> meals = List.of(meal);

		user.setMeals(meals);
		UserDTO dto = mapper.mapToUserDTO(user);
		double totalCalories = dto.getDailyNorm();

		Mockito.when(mealService.getByUserIdAndDate(1, LocalDate.parse(date) )).thenReturn(meals);
		Mockito.when(service.countCalories(meals)).thenReturn(totalCalories);

		mockMvc.perform(get(request))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.totalCalories", is(totalCalories)))
				.andDo(print());
	}

	@Test
	void testGetDailyResult() throws Exception{
		User user  = new User("name1", "email1@mail.ru", 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE);

		String date = "2025-10-12";
		String request = PATH+"?date="+date;

		Dish dish1 = new Dish("name1", 800, 1,1,1);
		Dish dish2 = new Dish("name2", 852, 1,1,1);
		List<Dish> dishes = List.of(dish1,dish2);

		Meal meal = new Meal(LocalDate.parse(date));
		meal.setDishes(dishes);
		meal.setUser(user);

		List<Meal> meals = List.of(meal);

		user.setMeals(meals);
		UserDTO dto = mapper.mapToUserDTO(user);
		double totalCalories = dto.getDailyNorm();

		Mockito.when(mealService.getByUserIdAndDate(1, LocalDate.parse(date) )).thenReturn(meals);
		Mockito.when(service.countCalories(meals)).thenReturn(totalCalories);

		mockMvc.perform(get(request))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.totalCalories", is(totalCalories)))
				.andDo(print());
	}

	@Test
	void testMealHistoryShouldReturnOk() throws Exception{
		int id=1;
		User user  = new User(id,"name1", "email1@mail.ru", 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE);

		String date = "2025-10-12";
		String request = PATH+"/history";

		Dish dish1 = new Dish("name1", 800, 1,1,1);
		Dish dish2 = new Dish("name2", 852, 1,1,1);
		List<Dish> dishes = List.of(dish1,dish2);

		Meal meal = new Meal(LocalDate.parse(date));
		meal.setDishes(dishes);
		meal.setUser(user);

		List<Meal> meals = List.of(meal);

		user.setMeals(meals);


		Mockito.when(mealService.getByUserIdAndDate(id, LocalDate.parse(date))).thenReturn(meals);

		mockMvc.perform(get(request))
				.andExpect(status().isOk())
				.andDo(print());
	}

}
