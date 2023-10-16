package edu.curtin.assignment2.coreapp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class Event {
    private String eventName;
    private LocalDateTime eventDate;
    private int eventDuration;

    public Event(String eventName, LocalDateTime eventDate, int eventDuration) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
    }

    public String getEventDetails(ResourceBundle bundle) {
        if (eventDuration != 0) {
            try {
                return eventName + "\n " + bundle.getString("ui_time") + ": " + eventDate.toLocalTime() + "\n "
                        + bundle.getString("ui_duration") + ": " + eventDuration + " " + bundle.getString("ui_mins");
            } catch (Exception e) {
                return eventName + "\n Time: " + eventDate.toLocalTime() + "\n Duration: " + eventDuration + " Mins";
            }
        } else {
            return eventName;
        }
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public int getEventDuration() {
        return eventDuration;
    }

    public int getRowIndex() {
        return eventDate.getHour();
    }

    public int getColumnIndex(LocalDateTime currentDate) {
        return (int) ChronoUnit.DAYS.between(currentDate.toLocalDate(), eventDate.toLocalDate());
    }

    public int getEventRows() {
        return (int) Math.ceil((double) eventDuration / 60);
    }
}
