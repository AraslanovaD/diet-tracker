package diet.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Length(min=2, max=20,message = "Name must be between 2 and 20 characters long")
    private String name;

    @Column(name = "email")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email address")
    private String email;

    @Column(name = "age")
    @Min(value = 0, message = "The value must be positive")
    private int age;

    @Column(name = "weight")
    @Min(value = 0, message = "The value must be positive")
    private double weight;

    @Column(name = "height")
    @Min(value = 0, message = "The value must be positive")
    private double height;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal")
    private Goal goal;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    private List<Meal> meals;

    public User() {}

    public User(int id, String name, String email, int age, double weight, double height, Goal goal, Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.gender = gender;
    }

    public User(String name, String email, int age, double weight, double height, Goal goal, Gender gender) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "Name cannot be blank") @Length(min = 2, max = 20, message = "Name must be between 2 and 20 characters long") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be blank") @Length(min = 2, max = 20, message = "Name must be between 2 and 20 characters long") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email address") String email) {
        this.email = email;
    }

    @Min(value = 0, message = "The value must be positive")
    public int getAge() {
        return age;
    }

    public void setAge(@NotBlank(message = "Age cannot be blank") @Min(value = 0, message = "The value must be positive") int age) {
        this.age = age;
    }

    @Min(value = 0, message = "The value must be positive")
    public double getWeight() {
        return weight;
    }

    public void setWeight(@Min(value = 0, message = "The value must be positive") double weight) {
        this.weight = weight;
    }

    @Min(value = 0, message = "The value must be positive")
    public double getHeight() {
        return height;
    }

    public void setHeight(@Min(value = 0, message = "The value must be positive") double height) {
        this.height = height;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
