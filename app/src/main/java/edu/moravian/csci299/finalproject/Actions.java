package edu.moravian.csci299.finalproject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * An Event object contains all of the information about a single event or
 * assignment due date.
 *
 * NOTE: this class is complete.
 */
@Entity
public class Actions {
    /**
     * The id of the event is the primary key in the database.
     */
    @PrimaryKey
    @NonNull
    public UUID id = UUID.randomUUID();
    /**
     * Start time for the event. If the endTIme is null, this represents the due date.
     */
    @NonNull
    public Date startTime = new Date();
    /**
     * The ending time. If the endTime is null then this "event" is actually an
     * assignment with a due date (the start time).
     */
    public Date endTime = null;
    /**
     * Name of the event.
     */
    @NonNull
    public String name = "New Action";
    /**
     * The type of the event.
     */
//    @NonNull
//    public ActionType type = ActionType.GENERIC;
    /**
     * The description of the event.
     */
    @NonNull
    public String description = "";
}

