package edu.moravian.csci299.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface BudgetDAO {
    /**
     * @return live-data view of all events on the calendar
     */
    @Query("SELECT * FROM actions")
    LiveData<List<Actions>> getAllActions();

    /**
     * Get an event from its ID.
     * @param id the
     * @return live-data view of a single event on the calendar
     */
    @Query("SELECT * FROM actions WHERE id=(:id) LIMIT 1")
    LiveData<Actions> getActionById(UUID id);

    /**
     * Get all events between the start and end dates given. This will include any events that start
     * or end within that range of date-times.
     * @param start the start date
     * @param end the end date
     * @return live-data view of a list of all events on the calendar between those dates
     */
    @Query("SELECT * FROM actions WHERE startTime BETWEEN (:start) AND (:end) OR endTime BETWEEN (:start) AND (:end)")
    LiveData<List<Actions>> getActionsBetween(Date start, Date end);

    /**
     * Get all events in a given 24 hour period starting at the given date. This will include any
     * events that starr or end within that range of date-times.
     * @param date the date at the beginning of the 24 hour period
     * @return live-data view of a list of all events on the calendar starting at the given date and
     *         ending within 24 hours
     */
    @Query("SELECT * FROM actions WHERE startTime BETWEEN (:date) AND (:date + 24*60*60*1000) OR endTime BETWEEN (:date) AND (:date + 24*60*60*1000)")
    LiveData<List<Actions>> getActionsOnDay(Date date);

    /**
     * Add an event to the database.
     * @param event the event to add
     */
    @Insert
    void addActions(Actions event);

    /**
     * Update an event in the database.
     * @param event the event to update
     */
    @Update
    void updateAction(Actions event);

    /**
     * Remove an event in the database.
     * @param event the event to remove
     */
    @Delete
    void removeAction(Actions event);
}
