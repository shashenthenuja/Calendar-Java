package edu.curtin.assignment2.coreapp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import edu.curtin.assignment2.terminalgrid.TerminalGrid;

public class Control {
    private String lang = "";
    private Scanner scan = new Scanner(System.in);
    private ResourceBundle bundle;
    private LocalDateTime currentDateTime;
    private Locale locale;
    private List<Event> eventList;

    public Control(LocalDateTime currentDateTime, List<Event> eventList) {
        this.currentDateTime = currentDateTime;
        this.eventList = eventList;
    }

    public void start() {
        languageMenu();
        setLocale(lang);
        displayCalendar();
        boolean cont = true;
        while (cont) {
            System.out.print("\n> ");
            String userInput = scan.next();
            switch (userInput) {
                case "+d":
                    currentDateTime = currentDateTime.plusDays(1);
                    displayCalendar();
                    break;
                case "+w":
                    currentDateTime = currentDateTime.plusWeeks(1);
                    displayCalendar();
                    break;
                case "+m":
                    currentDateTime = currentDateTime.plusMonths(1);
                    displayCalendar();
                    break;
                case "+y":
                    currentDateTime = currentDateTime.plusYears(1);
                    displayCalendar();
                    break;
                case "-d":
                    currentDateTime = currentDateTime.minusDays(1);
                    displayCalendar();
                    break;
                case "-w":
                    currentDateTime = currentDateTime.minusWeeks(1);
                    displayCalendar();
                    break;
                case "-m":
                    currentDateTime = currentDateTime.minusMonths(1);
                    displayCalendar();
                    break;
                case "-y":
                    currentDateTime = currentDateTime.minusYears(1);
                    displayCalendar();
                    break;
                case "t":
                    currentDateTime = LocalDateTime.now();
                    displayCalendar();
                    break;
                case "s":
                    displayCalendar();
                    searchEvents();
                    break;
                case "N":
                    eventList.add(new Event("New Event", LocalDateTime.of(2023, 10, 29, 02, 0), 20));
                    eventList.add(new Event("New Event", LocalDateTime.of(2023, 10, 02, 03, 0), 60));
                    eventList.add(new Event("New Event", LocalDateTime.of(2023, 11, 01, 00, 0), 90));
                    displayCalendar();
                    System.out.println();
                    break;
                case "M":
                    eventList.add(new Event("All Day Event", LocalDateTime.of(2023, 11, 03, 00, 0), 0));
                    displayCalendar();
                    System.out.println();
                    break;
                case "quit":
                    cont = false;
                    break;
                default:
                    displayCalendar();
                    break;
            }
        }
    }

    public void displayCalendar() {
        var terminalGrid = TerminalGrid.create();
        
        var listMessages = new ArrayList<List<String>>();
        var rows = new ArrayList<String>();
        var cols = new ArrayList<String>();

        System.out.print("\033[H\033[2J");
        System.out.println(bundle.getString("ui_chosen_lang") + " : " + locale.getDisplayLanguage() + "\n");

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", locale);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm", locale);

        for (int i = 0; i < 7; i++) {
            LocalDateTime futureDateTime = currentDateTime.plusDays(i);
            String formattedDate = dateFormat.format(futureDateTime);
            cols.add(formattedDate);
        }

        for (int i = 0; i < 4; i++) {
            LocalTime currentTime = LocalTime.of(i, 0);
            String formattedTime = timeFormat.format(currentTime);
            rows.add(formattedTime);
        }
        try {
            rows.add(bundle.getString("ui_all_day_events"));
        } catch (Exception e) {
            rows.add("All Day \nEvents");
        }        

        for (int i = 0; i <= 4; i++) {
            var row = new ArrayList<String>();
            for (int j = 0; j < 7; j++) {
                String val = " ";
                if (!eventList.isEmpty()) {
                    for (Event e : eventList) {
                        if (e.getColumnIndex(currentDateTime) == j && e.getRowIndex() == i && e.getEventDuration() != 0) {
                            val = e.getEventDetails(bundle);
                        }else if (e.getColumnIndex(currentDateTime) == j && e.getEventDuration() == 0 && i == 4) {
                            val = e.getEventDetails(bundle);
                        }
                    }
                }
                row.add(val);
            }
            listMessages.add(row);
        }

        terminalGrid.print(listMessages, rows, cols);
        System.out.println();

    }

    public void searchEvents() {
        try {
            System.out.print("\n" + bundle.getString("ui_search_events") + " > ");
        } catch (Exception e) {
            System.out.print("\nSearch events > ");
        }
        String query = scan.next();
        Event result = null;
        for (Event event : eventList) {
            if (event.getEventName().toLowerCase().contains(query.toLowerCase())) {
                if (!event.getEventDate().isBefore(LocalDateTime.now()) 
                    && !event.getEventDate().isAfter(LocalDateTime.now().plusYears(1))) {
                    result = event;
                    break;
                }
            }
        }
        if (result != null) {
            currentDateTime = result.getEventDate();
            displayCalendar();
            try {
                
                System.out.println(bundle.getString("ui_event_details")+ " : \n " + result.getEventDetails(bundle));
            } catch (Exception e) {
                System.out.println("Event Details : \n " + result.getEventDetails(bundle));
            }
        }else {
            displayCalendar();
            try {
                System.out.println(bundle.getString("ui_event_not_found") + " " + query);
            } catch (Exception e) {
                System.out.println("Event not found for query " + query);
            }
        }
    }

    public void languageMenu() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.println("Choose your language : ");
            System.out.println("[0] System Default ");
            System.out.println("[1] Custom Language (Eg. en-US, si-LK) \n");
            System.out.print("\n> ");
            String userInput = scan.next();
            if (userInput.equals("0")) {
                lang = "default";
                break;
            } else if (userInput.equals("1")) {
                System.out.print("\033[H\033[2J");
                System.out.print("Enter your language (Eg. en-US, si-LK) :  ");
                lang = scan.next();
                break;
            } else {
                System.out.println("Enter a valid choice!");
            }
        }
    }

    public void setLocale(String lang) {
        if (lang.equals("default")) {
            locale = Locale.US;
            bundle = ResourceBundle.getBundle("bundle", locale);
        } else {
            try {
                locale = Locale.forLanguageTag(lang);
                bundle = ResourceBundle.getBundle("bundle", locale);
            } catch (Exception e) {
                locale = Locale.forLanguageTag(lang);
                bundle = ResourceBundle.getBundle("bundle", Locale.US);
            }
        }
    }
}
