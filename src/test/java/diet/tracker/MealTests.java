package diet.tracker;

import com.fasterxml.jackson.databind.ObjectMapper;

import diet.tracker.entity.Meal;
import diet.tracker.exception.NoSuchDataException;

import diet.tracker.service.MealService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
class MealTests {

	private static final String PATH = "/api/meals";

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MealService service;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void testGetAllShouldReturnOk() throws Exception{
		List<Meal> meals = new ArrayList<>();

		LocalDate date1 = LocalDate.of(2025, 10,12);
		LocalDate date2 = LocalDate.of(2025, 11,12);

		meals.add(new Meal(date1));
		meals.add(new Meal(date2));

		Mockito.when(service.getAll()).thenReturn(meals);

		mockMvc.perform(get(PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].date", is("2025-10-12")))
				.andExpect(jsonPath("$[1].date", is("2025-11-12")))
				.andDo(print());
	}

	@Test
	void testGetShouldReturnOk() throws Exception{
		int id=1;
		String request = PATH + "/" +id;
		LocalDate date = LocalDate.of(2025, 10,12);

		Meal meal = new Meal(date);

		Mockito.when(service.get(id)).thenReturn(meal);

		mockMvc.perform(get(request))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.date", is("2025-10-12")))
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
	void testAddShouldReturnCreated() throws Exception{
		Meal newMeal = new Meal(LocalDate.of(2025, 10,12));
		String requestBody = objectMapper.writeValueAsString(newMeal);
		newMeal.setId(1);
		Mockito.when(service.save(newMeal)).thenReturn(newMeal);


		mockMvc.perform(post(PATH).contentType("application/json").content(requestBody))
				.andExpect(status().isCreated()).andDo(print());

	}

	@Test
	void testAddShouldReturnBadRequest() throws Exception{
		Meal newMeal = new Meal();

		String requestBody = objectMapper.writeValueAsString(newMeal);

		mockMvc.perform(post(PATH).contentType("application/json")
						.content(requestBody))
				.andExpect(status().isBadRequest())
				.andDo(print());

	}

	@Test
	void testUpdateShouldReturnOk() throws Exception{
		int id=1;

		Meal meal = new Meal(LocalDate.of(2025, 10,12));

		Mockito.when(service.save(meal)).thenReturn(meal);

		String requestBody = objectMapper.writeValueAsString(meal);

		mockMvc.perform(put(PATH).contentType("application/json").content(requestBody))
				.andExpect(status().isOk())
				.andDo(print());

	}

	@Test
	void testUpdateShouldReturnBadRequest() throws Exception{
		Meal meal = new Meal();

		Mockito.when(service.save(meal)).thenReturn(meal);

		String requestBody = objectMapper.writeValueAsString(meal);

		mockMvc.perform(put(PATH).contentType("application/json").content(requestBody))
				.andExpect(status().isBadRequest())
				.andDo(print());

	}

	@Test
	public void testDeleteShouldReturn404NotFound() throws Exception {
		int id = 1;
		String request = PATH + "/" + id;

		Mockito.doThrow(NoSuchDataException.class).when(service).delete(id);

		mockMvc.perform(delete(request))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	public void testDeleteShouldReturn200OK() throws Exception {
		int id = 1;
		String request = PATH + "/" + id;

		Mockito.doNothing().when(service).delete(id);

		mockMvc.perform(delete(request))
				.andExpect(status().isNoContent())
				.andDo(print());
	}
}
