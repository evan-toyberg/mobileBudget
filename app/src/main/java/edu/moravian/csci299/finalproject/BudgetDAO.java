package edu.moravian.csci299.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Dao
public interface BudgetDAO {
    /**
     * @return live-data view of all events on the calendar
     */
    @Query("SELECT * FROM `Action`")
    LiveData<List<Action>> getAllActions();

    /**
     * Get an event from its ID.
     * @param id the
     * @return live-data view of a single event on the calendar
     */
    @Query("SELECT * FROM `Action` WHERE id=(:id) LIMIT 1")
    LiveData<Action> getActionById(UUID id);


    /**
     * Get all events in a given 24 hour period starting at the given date. This will include any
     * events that starr or end within that range of date-times.
     * @param date the date at the beginning of the 24 hour period
     * @return live-data view of a list of all events on the calendar starting at the given date and
     *         ending within 24 hours
     */
    @Query("SELECT * FROM `Action` WHERE endTime BETWEEN (:date) AND (:date + 24*60*60*1000) OR endTime BETWEEN (:date) AND (:date + 24*60*60*1000)")
    LiveData<List<Action>> getActionsOnDay(Date date);



    /**
     * Add an event to the database.
     * @param action the event to add
     */
    @Insert
    void addActions(Action action);

    /**
     * Update an event in the database.
     * @param action the event to update
     */
    @Update
    void updateAction(Action action);

    /**
     * Remove an event in the database.
     * @param action the event to remove
     */
    @Delete
    void removeAction(Action action);
}
