package edu.moravian.csci299.finalproject;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BudgetRepository {
    private final BudgetDataBase database;
    private final BudgetDAO budgetDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private BudgetRepository(Context context) {
        database = Room.databaseBuilder(
                context.getApplicationContext(),
                BudgetDataBase.class,
                "calendar_database").build();
        budgetDao = database.budgetDao();
    }

    public LiveData<List<Actions>> getAllActions() {
        return budgetDao.getAllActions();
    }

    public LiveData<Actions> getActionById(UUID id) {
        return budgetDao.getActionById(id);
    }

    public LiveData<List<Actions>> getActionsBetween(Date start, Date end) {
        return budgetDao.getActionsBetween(start, end);
    }

    public LiveData<List<Actions>> getActionsOnDay(Date day) {
        return budgetDao.getActionsOnDay(day);
    }

    public void addAction(Actions action) {
        executor.execute(() -> {
            budgetDao.addActions(action);
        });
    }

    public void removeAction(Actions action) {
        executor.execute(() -> {
            budgetDao.removeAction(action);
        });
    }

    public void updateAction(Actions action) {
        executor.execute(() -> {
            budgetDao.updateAction(action);
        });
    }

    // The single instance of the repository
    private static BudgetRepository INSTANCE;

    public static BudgetRepository get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("BudgetRepository must be initialized");
        }
        return INSTANCE;
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new BudgetRepository(context);
        }
    }
}
