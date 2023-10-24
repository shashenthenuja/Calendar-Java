package edu.curtin.assignment2.coreapp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import edu.curtin.assignment2.pluginapi.Event;
import edu.curtin.assignment2.terminalgrid.TerminalGrid;

/* *******************************************************************
* File:       Control.java
* Author:     G.G.T.Shashen
* Created:    14/10/2023
* Modified:   25/10/2023
* Desc:       Control class to handle the main functionality of the calendar
***********************************************************************/
public class Control {
    private Scanner scan = new Scanner(System.in);
    private ResourceBundle bundle;
    private LocalDateTime currentDateTime;
    private Locale locale;
    private List<Event> eventList;
    private List<LoaderAPI> notifyList;

    public Control(LocalDateTime currentDateTime, List<Event> eventList, List<LoaderAPI> notifyList, ResourceBundle bundle, Locale locale) {
        this.currentDateTime = currentDateTime;
        this.eventList = eventList;
        this.notifyList = notifyList;
        this.bundle = bundle;
        this.locale = locale;
    }

    /* start the calendar controls to manipulate the calendar */
    public void start() throws ReflectiveOperationException {
        displayCalendar();
        boolean cont = true;
        while (cont) {
            // load the notify plugins if present
            notifyEvents();
            System.out.print("\n> ");
            String userInput = scan.next();
            switch (userInput) {
                // increment current date by one day
                case "+d":
                    currentDateTime = currentDateTime.plusDays(1);
                    displayCalendar();
                    break;
                // increment current date by one week
                case "+w":
                    currentDateTime = currentDateTime.plusWeeks(1);
                    displayCalendar();
                    break;
                // increment current date by one month
                case "+m":
                    currentDateTime = currentDateTime.plusMonths(1);
                    displayCalendar();
                    break;
                // increment current date by one year
                case "+y":
                    currentDateTime = currentDateTime.plusYears(1);
                    displayCalendar();
                    break;
                // decrement current date by one day
                case "-d":
                    currentDateTime = currentDateTime.minusDays(1);
                    displayCalendar();
                    break;
                // decrement current date by one week
                case "-w":
                    currentDateTime = currentDateTime.minusWeeks(1);
                    displayCalendar();
                    break;
                // decrement current date by one month
                case "-m":
                    currentDateTime = currentDateTime.minusMonths(1);
                    displayCalendar();
                    break;
                // decrement current date by one year
                case "-y":
                    currentDateTime = currentDateTime.minusYears(1);
                    displayCalendar();
                    break;
                // change the current date to current system date
                case "t":
                    currentDateTime = LocalDateTime.now();
                    displayCalendar();
                    break;
                // handle search function
                case "s":
                    displayCalendar();
                    searchEvents();
                    break;
                // exit the application
                case "quit":
                    cont = false;
                    break;
                default:
                    displayCalendar();
                    break;
            }
        }
    }

    /* display the created calendar to the user  */
    public void displayCalendar() {
        // create the terminal grid to represent the calendar
        var terminalGrid = TerminalGrid.create();

        var listMessages = new ArrayList<List<String>>();
        var rows = new ArrayList<String>();
        var cols = new ArrayList<String>();

        // show the current chosen language to the user
        System.out.print("\033[H\033[2J");
        System.out.println(bundle.getString("ui_chosen_lang") + " : " + locale.getDisplayLanguage() + "\n");

        // format the dates and times of the calendar according to the selected locale
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", locale);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a", locale);

        // set the dates of the calendar
        for (int i = 0; i < 7; i++) {
            LocalDateTime futureDateTime = currentDateTime.plusDays(i);
            String formattedDate = dateFormat.format(futureDateTime);
            cols.add(formattedDate);
        }

        // set the times of the calendar
        for (int i = 0; i < 24; i++) {
            LocalTime currentTime = LocalTime.of(i, 0);
            String formattedTime = timeFormat.format(currentTime);
            rows.add(formattedTime);
        }

        // add a new inernationalized row to represent all day events
        try {
            rows.add(bundle.getString("ui_all_day_events"));
        } catch (MissingResourceException  e) {
            rows.add("All Day \nEvents");
        }

        // populate the events to the calendar accordingly
        for (int i = 0; i <= 24; i++) {
            var row = new ArrayList<String>();
            for (int j = 0; j < 7; j++) {
                String val = " ";
                if (!eventList.isEmpty()) {
                    for (Event e : eventList) {
                        // display the events according to the respective rows
                        if (e.getColumnIndex(currentDateTime) == j && e.getRowIndex() == i
                                && e.getEventDuration() != 0) {
                            val = e.getEventDetails(bundle, locale);
                        // display all day events
                        } else if (e.getColumnIndex(currentDateTime) == j && e.getEventDuration() == 0 && i == 24) {
                            val = e.getEventDetails(bundle, locale);
                        }
                    }
                }
                row.add(val);
            }
            listMessages.add(row);
        }

        // display the populated calendar to the user
        terminalGrid.print(listMessages, rows, cols);
        System.out.println();

    }

    /* handle and display the events that the user searches */
    public void searchEvents() {
        try {
            System.out.print("\n" + bundle.getString("ui_search_events") + " > ");
        } catch (MissingResourceException  e) {
            System.out.print("\nSearch events > ");
        }
        // clear any new lines left
        scan.nextLine();
        String query = scan.nextLine();
        Event result = null;
        // find and get the respective searched event
        for (Event event : eventList) {
            if (event.getEventName().toLowerCase().trim().contains(query.toLowerCase().trim())) {
                if (!event.getEventDate().toLocalDate().isBefore(LocalDateTime.now().toLocalDate())
                        && !event.getEventDate().toLocalDate()
                                .isAfter(LocalDateTime.now().toLocalDate().plusYears(1))) {
                    result = event;
                    break;
                }
            }
        }
        if (result != null) {
            // update the current date to the found event date and display the calendar
            currentDateTime = result.getEventDate();
            displayCalendar();
            // display the event details under the calendar
            try {
                System.out.println(bundle.getString("ui_results") + " " + query + " :- \n");
                System.out.println(
                        bundle.getString("ui_event_details") + " : \n\n" + result.getSearchEventDetails(bundle, locale));
            } catch (MissingResourceException e) {
                System.out.println("Results for " + query + " : \n");
                System.out.println("Event Details : \n\n" + result.getSearchEventDetails(bundle, locale));
            }
        } else {
            // display the calendar only if events are not found
            displayCalendar();
            try {
                System.out.println(bundle.getString("ui_event_not_found") + " " + query + "!");
            } catch (MissingResourceException e) {
                System.out.println("Event not found for query " + query + "!");
            }
        }
    }

    /* load the retrieved notify plugins */
    public void notifyEvents() throws ReflectiveOperationException {
        for (LoaderAPI api : notifyList) {
            if (api != null) {
                new PluginLoader().loadPlugin("edu.curtin.calplugins.Notify", api);
            }
        }
    }

}
