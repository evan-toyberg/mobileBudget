package edu.moravian.csci299.MobileBudget;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * An action
 * object contains all of the information about an action
 *
 */
@Entity
public class Action {
    /**
     * The id of the action
     * is the primary key in the database.
     */
    @PrimaryKey
    @NonNull
    public UUID id = UUID.randomUUID();
    /**
     * Start time for the action
     * . If the endTIme is null, this represents the due date.
     */
    @NonNull
    public String amount = "";

    /**
     * End time of action
     */
    @NonNull
    public Date endTime = null;
    /**
     * Name of the action
     * .
     */
    @NonNull
    public String name = "New Action";
    /**
     * The type of the action.
     */
    @NonNull
    public ActionType type = ActionType.INCOME;
    /**
     * The description of the action.
     */
    @NonNull
    public String description = "";
}

