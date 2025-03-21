create table users(
id serial primary key,
name varchar(20) not null,
email varchar(254) not null, 
age int not null,
weight int not null,
height int not null,
goal varchar(8) not null,
gender varchar(6) not null
);

create table dish(
id serial primary key,
name varchar(60) not null,
calories real not null,
proteins real not null,
fats real not null,
carbohydrates real not null
);

create table meal(
id serial primary key,
users_id int not null,
date date not null,
foreign key (users_id) references user
);

create table meal_dish(
id serial primary key,
meal_id int not null,
dish_id int not null,
foreign key (meal_id) references meal,
foreign key (dish_id) references dish
);

insert into users(name, email, age, weight, height, goal, gender)
values('Mari', 'mari123@gmail.com', 26, 58, 165, 'MAINTAIN', 'FEMALE');

insert into users(name, email, age, weight, height, goal, gender)
values('Tom', 'tom.b@gmail.com', 40, 100, 170, 'LOSS', 'MALE');

insert into dish(name, calories, proteins, fats, carbohydrates)
values("waffles", 210.9, 5.5, 6.5, 34.9);

insert into dish(name, calories, proteins, fats, carbohydrates)
values("vinegret", 130.1, 1.7, 10.3, 8.2);

insert into meal(users_id, date)
values(1, '2025-10-12');

insert into meal(users_id, date)
values(2, '2025-11-12');

insert into meal_dish(meal_id, dish_id)
values(1, 1);

insert into meal_dish(meal_id, dish_id)
values(1, 2);

insert into meal_dish(meal_id, dish_id)
values(2, 2);

insert into meal_dish(meal_id, dish_id)
values(2, 2);
