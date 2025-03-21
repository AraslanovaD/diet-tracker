package diet.tracker.dto;

import diet.tracker.entity.Gender;
import diet.tracker.entity.Goal;
import jakarta.persistence.*;

public class UserDTO {
    private int id;

    private String name;

    private String email;

    private int age;

    private double weight;

    private double height;

    private Gender gender;

    private Goal goal;

    private double dailyNorm;

    public UserDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public double getDailyNorm() {
        return dailyNorm;
    }

    public void setDailyNorm() {
        double dailyNorm;

        if(this.gender==Gender.MALE){
            dailyNorm = 88.36 + ( 13.4 * this.weight) + ( 4.8 * this.height ) - ( 5.7 * this.age);
        } else {
            dailyNorm = 447.6 + ( 9.2 * this.weight) + ( 3.1 * this.height ) - ( 4.3 * this.age);
        }

        if(this.goal==Goal.LOSS){
            dailyNorm -= 500;
        } else if(this.goal==Goal.GAIN){
            dailyNorm += 500;
        }

        this.dailyNorm = dailyNorm;
    }

    public void setDailyNorm(double dailyNorm) {
        this.dailyNorm = dailyNorm;
    }
}
