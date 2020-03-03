package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return (em.createNamedQuery(Meal.UPDATE)
                    .setParameter("description", meal.getDescription())
                    .setParameter("calories", meal.getCalories())
                    .setParameter("date_time", meal.getDateTime())
                    .setParameter("id", meal.getId())
                    .setParameter("userId", userId)
                    .executeUpdate() != 0 ? meal : null);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        List<Meal> meals = em.createNamedQuery(Meal.FIND)
                .setParameter("userId", userId)
                .setParameter("id", id)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    @Transactional
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL).setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.ALLB)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}