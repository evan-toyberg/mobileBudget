package edu.moravian.csci299.MobileBudget;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Data access object for actions
 */
@Dao
public interface BudgetDAO {
    /**
     * @return live-data view of all action
     * s on the calendar
     */
    @Query("SELECT * FROM `Action`")
    LiveData<List<Action>> getAllActions();

    /**
     * Get an action from its ID.
     *
     * @param id the
     * @return live-data view of a single action
     * on the calendar
     */
    @Query("SELECT * FROM `Action` WHERE id=(:id) LIMIT 1")
    LiveData<Action> getActionById(UUID id);


    /**
     * Get all action s in a given 24 hour period starting at the given date. This will include any
     * action s that starr or end within that range of date-times.
     *
     * @param date the date at the beginning of the 24 hour period
     * @return live-data view of a list of all action
     * s on the calendar starting at the given date and
     * ending within 24 hours
     */
    @Query("SELECT * FROM `Action` WHERE endTime BETWEEN (:date) AND (:date + 24*60*60*1000) OR endTime BETWEEN (:date) AND (:date + 24*60*60*1000)")
    LiveData<List<Action>> getActionsOnDay(Date date);


    @Query("SELECT * FROM `Action` WHERE type=(:type)")
    LiveData<List<Action>> getAllByType(ActionType type);

    /**
     * Add an action to the database.
     *
     * @param action the action
     *               to add
     */
    @Insert
    void addActions(Action action);

    /**
     * Update an action in the database.
     *
     * @param action the action
     *               to update
     */
    @Update
    void updateAction(Action action);

    /**
     * Remove an action in the database.
     *
     * @param action the action
     *               to remove
     */
    @Delete
    void removeAction(Action action);
}
