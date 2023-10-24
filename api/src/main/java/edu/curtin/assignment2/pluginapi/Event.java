package edu.curtin.assignment2.pluginapi;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

/* *******************************************************************
* File:       Event.java
* Author:     G.G.T.Shashen
* Created:    14/10/2023
* Modified:   25/10/2023
* Desc:       Event class to handle event in the calendar
***********************************************************************/
public class Event {
    private String eventName;
    private LocalDateTime eventDate;
    private int eventDuration;

    public Event(String eventName, LocalDateTime eventDate, int eventDuration) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
    }

    /* get the current event details to display inside the calendar blocks */
    public String getEventDetails(ResourceBundle bundle, Locale locale) {
        if (eventDuration != 0) {
            // format the date and duration according to the current locale
            DateTimeFormatter date = DateTimeFormatter.ofPattern("HH:mm:ss a", locale);
            NumberFormat nf = NumberFormat.getInstance(locale);
            try {
                return eventName + "\n " + bundle.getString("ui_time") + ": " + date.format(eventDate.toLocalTime())
                        + "\n "
                        + bundle.getString("ui_duration") + ": " + nf.format(eventDuration) + " "
                        + bundle.getString("ui_mins");
            } catch (Exception e) {
                return eventName + "\n Time: " + date.format(eventDate.toLocalTime()) + "\n Duration: "
                        + nf.format(eventDuration) + " Mins";
            }
        } else {
            return eventName;
        }
    }

    /*
     * get the current event details to display as a search result or a notification
     */
    public String getSearchEventDetails(ResourceBundle bundle, Locale locale) {
        // format the date, time and duration according to the current locale
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd MMMM yyyy", locale);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss a", locale);
        NumberFormat nf = NumberFormat.getInstance(locale);
        // get either all day events or normal events
        if (eventDuration != 0) {
            try {
                return bundle.getString("ui_search_name") + ": " + eventName + "\n "
                        + bundle.getString("ui_search_date") + ": " + date.format(eventDate.toLocalDate()) + "\n "
                        + bundle.getString("ui_search_time") + ": " + time.format(eventDate.toLocalTime()) + "\n "
                        + bundle.getString("ui_search_duration") + ": " + nf.format(eventDuration) + " "
                        + bundle.getString("ui_mins");
            } catch (Exception e) {
                return "Event Name: " + eventName + "\n Event Date: " + date.format(eventDate.toLocalDate())
                        + "\n Event Time: "
                        + time.format(eventDate.toLocalTime())
                        + "\n Event Duration: " + nf.format(eventDuration) + " Mins";
            }
        } else {
            try {
                return bundle.getString("ui_search_name") + ": " + eventName + "\n" + bundle.getString("ui_search_date")
                        + ": " + date.format(eventDate.toLocalDate());
            } catch (Exception e) {
                return "Event Name: " + eventName + "\n Event Date: " + date.format(eventDate.toLocalDate());
            }
        }
    }

    /* get the current event name */
    public String getEventName() {
        return eventName;
    }

    /* get the current event date */
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    /* get the current event duration */
    public int getEventDuration() {
        return eventDuration;
    }

    /* get the calendar row index according to the hourse */
    public int getRowIndex() {
        return eventDate.getHour();
    }

    /*
     * get the calendar column index according to the current date and the event day
     */
    public int getColumnIndex(LocalDateTime currentDate) {
        return (int) ChronoUnit.DAYS.between(currentDate.toLocalDate(), eventDate.toLocalDate());
    }

    /* get the number of needed rows according to the duration of the event */
    public int getEventRows() {
        return (int) Math.ceil((double) eventDuration / 60);
    }
}
