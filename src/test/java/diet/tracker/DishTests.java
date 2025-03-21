package diet.tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import diet.tracker.entity.Dish;
import diet.tracker.exception.NoSuchDataException;
import diet.tracker.service.DishService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
class DishTests {

	private static final String PATH = "/api/dishes";

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DishService service;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void testGetAllShouldReturnOk() throws Exception{
		List<Dish> dishes = new ArrayList<>();
		dishes.add(new Dish("name1", 25, 60, 170,88));
		dishes.add(new Dish("name2", 25, 60, 170,88));

		Mockito.when(service.getAll()).thenReturn(dishes);

		mockMvc.perform(get(PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].name", is("name1")))
				.andExpect(jsonPath("$[1].name", is("name2")))
				.andDo(print());
	}

	@Test
	void testGetShouldReturnOk() throws Exception{
		int id=1;
		String request = PATH + "/" +id;
		String name = "name1";

		Dish dish = new Dish(name, 1,2,3,4);

		Mockito.when(service.get(id)).thenReturn(dish);

		mockMvc.perform(get(request))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.name", is(name)))
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
		Dish newDish = new Dish("name1",1,2,3,4);
		String requestBody = objectMapper.writeValueAsString(newDish);
		newDish.setId(1);
		Mockito.when(service.save(newDish)).thenReturn(newDish);


		mockMvc.perform(post(PATH).contentType("application/json").content(requestBody))
				.andExpect(status().isCreated()).andDo(print());

	}

	@Test
	void testAddShouldReturnBadRequest() throws Exception{
		Dish newDish = new Dish("",1,2,3,4);

		String requestBody = objectMapper.writeValueAsString(newDish);

		mockMvc.perform(post(PATH).contentType("application/json")
						.content(requestBody))
				.andExpect(status().isBadRequest())
				.andDo(print());

	}

	@Test
	void testUpdateShouldReturnOk() throws Exception{
		int id=1;

		Dish dish = new Dish(id,"name1",1,2,3,4);

		Mockito.when(service.save(dish)).thenReturn(dish);

		String requestBody = objectMapper.writeValueAsString(dish);

		mockMvc.perform(put(PATH).contentType("application/json").content(requestBody))
				.andExpect(status().isOk())
				.andDo(print());

	}

	@Test
	void testUpdateShouldReturnBadRequest() throws Exception{
		int id=1;
		String name = "name1";

		Dish dish = new Dish(id,"",1,2,3,4);

		Mockito.when(service.save(dish)).thenReturn(dish);

		String requestBody = objectMapper.writeValueAsString(dish);

		mockMvc.perform(put(PATH).contentType("application/json").content(requestBody))
				.andExpect(status().isBadRequest())
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

	@Test
	public void testDeleteShouldReturn404NotFound() throws Exception {
		int id = 1;
		String request = PATH + "/" + id;

		Mockito.doThrow(NoSuchDataException.class).when(service).delete(id);

		mockMvc.perform(delete(request))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

}
