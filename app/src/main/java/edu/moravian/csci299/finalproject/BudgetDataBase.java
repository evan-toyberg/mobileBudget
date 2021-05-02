package edu.moravian.csci299.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Connection to the database of actons
 */
@Database(entities = {Action.class}, version = 1)
@TypeConverters(BudgetTypeConverter.class)
public abstract class BudgetDataBase extends RoomDatabase {
    public abstract BudgetDAO budgetDao();
}
