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
        Map<LocalDate, Integer> mapSumOfCalories = new HashMap<>();
        Map<LocalDate, List<Integer>> mapIndexes = new HashMap<>();
        for (UserMeal userMeal: meals) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            boolean excess = (mapSumOfCalories.merge(localDate, userMeal.getCalories(), Integer::sum) > caloriesPerDay);
            if (excess) {
                List<Integer> indexesList = mapIndexes.getOrDefault(localDate, new ArrayList<>());
                for (int j: indexesList) {
                    UserMealWithExcess userMealWithExcess = userMealWithExcesses.get(j);
                    userMealWithExcesses.set(j, new UserMealWithExcess(
                            userMeal.getDateTime(),
                            userMealWithExcess.getDescription(),
                            userMealWithExcess.getCalories(),
                            true));
                }
                mapIndexes.remove(localDate);
            }
            if (TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess));
                if (!excess) {
                    mapIndexes.computeIfAbsent(localDate, key -> new ArrayList<>()).add(userMealWithExcesses.size() - 1);
                }
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapSumOfCalories = meals.stream()
                .collect(Collectors.groupingBy(eventDataRow -> eventDataRow.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(userMeal -> {
                    LocalTime localTime = userMeal.getDateTime().toLocalTime();
                    return TimeUtil.isBetweenInclusive(localTime, startTime, endTime);
                })
                .map(userMeal ->
                        new UserMealWithExcess(
                                userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                mapSumOfCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
