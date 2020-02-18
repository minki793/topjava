package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.MealTo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private MealService service;
    protected final Logger log = LoggerFactory.getLogger(MealRestController.class);

    public MealRestController() {
        service = new MealService(new InMemoryMealRepository());
    }

    public List<MealTo> getAll() {
        log.info("getAll", authUserId());
        return  MealsUtil.getTos(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int id) {
        log.info("get {}", id, authUserId());
        return service.get(id, authUserId());
    }

    public Meal create(LocalDateTime dateTime, String description, int calories) {
        log.info("create {}");
        Meal meal = new Meal(dateTime, description, calories, authUserId());
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id, authUserId());
        service.delete(id, authUserId());
    }

    public void update(int id, LocalDateTime dateTime, String description, int calories) {
        log.info("update {} with id={}", id);
        Meal meal = new Meal(id, dateTime, description, calories, authUserId());
        service.update(meal);
    }
}