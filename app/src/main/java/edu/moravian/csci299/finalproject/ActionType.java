package edu.moravian.csci299.finalproject;

public enum ActionType {
    INCOME("Income", R.drawable.income_icon),
    EXPENSE("Expense", R.drawable.expenses_icon);

    public final String simpleName;
    public final int iconResourceId;
    ActionType(String action, int iconId) {
        this.simpleName = action;
        this.iconResourceId = iconId;
    }
}
