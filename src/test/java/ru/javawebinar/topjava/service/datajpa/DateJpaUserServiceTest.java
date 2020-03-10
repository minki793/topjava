package ru.javawebinar.topjava.service.datajpa;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DateJpaUserServiceTest extends UserServiceTest {
    @Override
    public void get() throws Exception {
        User user = service.get(USER_ID);
        User userUpdatedWithMeal = getUpdatedWithMeal();
        USER_MATCHER.assertMatch(user, userUpdatedWithMeal);
        MEAL_MATCHER.assertMatch(user.getMeals(), MEALS);
    }
}
