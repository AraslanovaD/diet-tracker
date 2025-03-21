package diet.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Length(min=2, max=60,message = "Name must be between 2 and 60 characters long")
    private String name;

    @Column(name = "calories")
    @Min(value = 0, message = "The value must be positive")
    private double calories;

    @Column(name = "proteins")
    @Min(value = 0, message = "The value must be positive")
    private double proteins;

    @Column(name = "fats")
    @Min(value = 0, message = "The value must be positive")
    private double fats;

    @Column(name = "carbohydrates")
    @Min(value = 0, message = "The value must be positive")
    private double carbohydrates;

    public Dish() {}

    public Dish(int id, String name, double calories, double proteins, double fats, double carbohydrates) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public Dish(String name, double calories, double proteins, double fats, double carbohydrates) {
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Min(value = 0, message = "The value must be positive")
    public double getCalories() {
        return calories;
    }

    public @NotBlank(message = "Name cannot be blank") @Length(min = 2, max = 60, message = "Name must be between 2 and 60 characters long") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be blank") @Length(min = 2, max = 60, message = "Name must be between 2 and 60 characters long") String name) {
        this.name = name;
    }

    public void setCalories(@Min(value = 0, message = "The value must be positive") double calories) {
        this.calories = calories;
    }

    @Min(value = 0, message = "The value must be positive")
    public double getProteins() {
        return proteins;
    }

    public void setProteins(@Min(value = 0, message = "The value must be positive") double proteins) {
        this.proteins = proteins;
    }

    @Min(value = 0, message = "The value must be positive")
    public double getFats() {
        return fats;
    }

    public void setFats(@Min(value = 0, message = "The value must be positive") double fats) {
        this.fats = fats;
    }

    @Min(value = 0, message = "The value must be positive")
    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(@Min(value = 0, message = "The value must be positive") double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}
