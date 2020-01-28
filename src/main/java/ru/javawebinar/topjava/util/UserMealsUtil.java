package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        Map<LocalDate, List<Integer>> mapList = new HashMap<>();
        for (UserMeal userMeal: meals) {
            LocalDateTime localDateTime = userMeal.getDateTime();
            LocalDate localDate = localDateTime.toLocalDate();
            int calories = userMeal.getCalories();
            calories = map.merge(localDate, calories, Integer::sum);
            boolean excess = (calories > caloriesPerDay);
            if (excess) {
                List<Integer> integerList = mapList.get(localDate);
                for (int y: integerList) {
                    UserMealWithExcess userMealWithExcess = userMealWithExcesses.get(y);
                    userMealWithExcesses.set(y, new UserMealWithExcess(localDateTime, userMealWithExcess.getDescription(), userMealWithExcess.getCalories(), true));
                }
            }
            if (localDateTime.toLocalTime().isBefore(endTime) && localDateTime.toLocalTime().isAfter(startTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(localDateTime, userMeal.getDescription(), userMeal.getCalories(), excess));
                if (!excess) {
                    List<Integer> ind = new ArrayList<>();
                    ind.add(userMealWithExcesses.size() - 1);
                    mapList.merge(localDate, ind, (oldv, newv) -> {
                        oldv.addAll(newv);
                        return oldv;
                    });
                }
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = meals.stream().collect(Collectors.groupingBy(eventDataRow -> eventDataRow.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream().filter(userMeal -> {LocalTime localTime = userMeal.getDateTime().toLocalTime();
        return localTime.isBefore(endTime) && localTime.isAfter(startTime);}).map(userMeal ->
        new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                map.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }
}
