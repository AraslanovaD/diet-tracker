package diet.tracker;

import diet.tracker.dto.UserDTO;
import diet.tracker.entity.Gender;
import diet.tracker.entity.Goal;
import diet.tracker.entity.Meal;
import diet.tracker.entity.User;
import diet.tracker.exception.NoSuchDataException;
import diet.tracker.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
class UserTests {

	private static final String PATH = "/api/users";

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testGetAllShouldReturnOk() throws Exception{
		List<User> userList = new ArrayList<>();
		userList.add(new User("name1", "email1@mail.ru", 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE));
		userList.add(new User("name2", "email2@mail.ru", 59, 70, 180, Goal.GAIN, Gender.MALE));

		Mockito.when(service.getAll()).thenReturn(userList);

		mockMvc.perform(get(PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].email", is("email1@mail.ru")))
				.andExpect(jsonPath("$[1].email", is("email2@mail.ru")))
				.andDo(print());
	}

	@Test
	void testGetShouldReturnOk() throws Exception{
		int id=1;
		String request = PATH + "/" +id;
		String email = "email1@mail.ru";

		User user = new User("name1", email, 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE);

		Mockito.when(service.get(id)).thenReturn(user);

		mockMvc.perform(get(request))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.email", is(email)))
				.andDo(print());
	}

	@Test
	void testGetShouldReturnNotFound() throws Exception{
		int id=1;
		String request = PATH + "/" +id;

		Mockito.when(service.get(id)).thenThrow(NoSuchDataException.class);

		mockMvc.perform(get(request))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	void testAddUserShouldReturnCreated() throws Exception{
		User user = new User("name1", "email1@mail.ru", 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE);
		String requestBody = objectMapper.writeValueAsString(user);

		User newUser = new User(1,"name1", "email1@mail.ru", 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE);

		Mockito.when(service.save(user)).thenReturn(newUser);


		mockMvc.perform(post(PATH).contentType("application/json").content(requestBody))
				.andExpect(status().isCreated()).andDo(print());

	}

	@Test
	void testAddUserShouldReturnBadRequest() throws Exception{
		User newUser = new User("name1", "email1@mail.ru", 60, -1, 170, Goal.MAINTAIN, Gender.FEMALE);

		String requestBody = objectMapper.writeValueAsString(newUser);

		mockMvc.perform(post(PATH).contentType("application/json")
						.content(requestBody))
				.andExpect(status().isBadRequest())
				.andDo(print());

	}

	@Test
	void testUpdateShouldReturnBadRequest() throws Exception{
		User user = new User(1,"", "email1@mail.ru", 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE);

		Mockito.when(service.save(user)).thenReturn(user);

		String requestBody = objectMapper.writeValueAsString(user);

		mockMvc.perform(put(PATH).contentType("application/json").content(requestBody))
				.andExpect(status().isBadRequest())
				.andDo(print());

	}

	@Test
	void testUpdateShouldReturnOk() throws Exception{
		User user = new User(1,"name1", "email1@mail.ru", 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE);

		Mockito.when(service.save(user)).thenReturn(user);

		String requestBody = objectMapper.writeValueAsString(user);

		mockMvc.perform(put(PATH).contentType("application/json").content(requestBody))
				.andExpect(status().isOk())
				.andDo(print());

	}

	@Test
	public void testDeleteUserShouldReturn200OK() throws Exception {
		int id = 1;
		String request = PATH + "/" + id;

		Mockito.doNothing().when(service).delete(id);

		mockMvc.perform(delete(request))
				.andExpect(status().isNoContent())
				.andDo(print());
	}

	@Test
	public void testDeleteUserShouldReturn404NotFound() throws Exception {
		int id = 1;
		String request = PATH + "/" + id;

		Mockito.doThrow(NoSuchDataException.class).when(service).delete(id);

		mockMvc.perform(delete(request))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	void testAddMealShouldReturnCreated() throws Exception{
		int id=1;
		String request = PATH + "/" +id+"/meals";

		Meal meal = new Meal(LocalDate.of(2025, 10,12));
		meal.setId(1);

		String requestBody = objectMapper.writeValueAsString(meal);


		User user = new User(1,"name1", "email1@mail.ru", 25, 60, 170, Goal.MAINTAIN, Gender.FEMALE);

		List<Meal> meals = new ArrayList<>();
		meals.add(meal);

		user.setMeals(meals);

		Mockito.when(service.addMeal(id, meal)).thenReturn(user);


		mockMvc.perform(post(request).contentType("application/json").content(requestBody))
				.andExpect(status().isCreated()).andDo(print());

	}

	@Test
	void testAddMealShouldReturnBadRequest() throws Exception{
		int id=1;
		String request = PATH + "/" +id+"/meals";

		Meal meal = new Meal();

		String requestBody = objectMapper.writeValueAsString(meal);

		mockMvc.perform(post(request).contentType("application/json")
						.content(requestBody))
				.andExpect(status().isBadRequest())
				.andDo(print());

	}

	@Test
	public void testDeleteMealShouldReturn200OK() throws Exception {
		int id = 1;
		int mealId = 1;
		String request = PATH + "/" + id+"/meals/"+mealId;

		Mockito.doNothing().when(service).removeMeal(id, mealId);

		mockMvc.perform(delete(request))
				.andExpect(status().isNoContent())
				.andDo(print());
	}

	@Test
	public void testDeleteMealShouldReturn404NotFound() throws Exception {
		int id = 1;
		int mealId = 1;
		String request = PATH + "/" + id+"/meals/"+mealId;

		Mockito.doThrow(NoSuchDataException.class).when(service).removeMeal(id, mealId);

		mockMvc.perform(delete(request))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

}
