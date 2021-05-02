package edu.moravian.csci299.finalproject;

/**
 * The different types of action types that can be displayed with an action
 */
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
