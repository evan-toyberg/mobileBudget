package edu.moravian.csci299.MobileBudget;

import android.app.Application;

public class BudgetApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BudgetRepository.initialize(this);
    }
}
