package edu.moravian.csci299.finalproject;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The singleton repository for the database.
 */
public class BudgetRepository {
    private final BudgetDataBase database;
    private final BudgetDAO budgetDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private BudgetRepository(Context context) {
        database = Room.databaseBuilder(
                context.getApplicationContext(),
                BudgetDataBase.class,
                "budget_database").build();
        budgetDao = database.budgetDao();
    }

    public LiveData<List<Action>> getAllActions() {
        return budgetDao.getAllActions();
    }

    public LiveData<Action> getActionById(UUID id) {
        return budgetDao.getActionById(id);
    }


    public LiveData<List<Action>> getIncomes() {
        return budgetDao.getAllByType(ActionType.INCOME);
    }
    public LiveData<List<Action>> getExpenses() {
        return budgetDao.getAllByType(ActionType.EXPENSE);
    }

    public void addAction(Action action) {
        executor.execute(() -> {
            budgetDao.addActions(action);
        });
    }

    public void removeAction(Action action) {
        executor.execute(() -> {
            budgetDao.removeAction(action);
        });
    }

    public void updateAction(Action action) {
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
