package ru.javawebinar.topjava.service.jpa;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles("jpa")
public class JpaUserServiceTest extends UserServiceTest {
}
