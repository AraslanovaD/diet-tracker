package diet.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="users_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name="meal_dish",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

    public Meal() {}

    public Meal(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Date cannot be null") LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull(message = "Date cannot be null") LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user +
                ", dishes=" + dishes +
                '}';
    }
}
