package edu.curtin.assignment2;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {
    static String userInput;
    static ResourceBundle bundle;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Scanner scan = new Scanner(System.in);
        String lang = chooseLang();

        while (true) {
            System.out.print("\033[H\033[2J");
            cal(lang);
            System.out.print("\n> ");
            userInput = scan.next();
        }
    }

    public static void cal(String lang) {
        var terminalGrid = TerminalGrid.create();

        var listMessages = new ArrayList<List<String>>();
        var rows = new ArrayList<String>();
        var cols = new ArrayList<String>();

        LocalDateTime currentDateTime = LocalDateTime.now();
        Locale locale;

        if (lang.equals("default")) {
            locale = Locale.US;
            bundle = ResourceBundle.getBundle("bundle", locale);
            System.out.println(bundle.getString("ui_chosen_lang") + " : " + locale.getDisplayLanguage() + "\n");
        } else {
            try {
                locale = Locale.forLanguageTag(lang);
                bundle = ResourceBundle.getBundle("bundle", locale);
                System.out.println(bundle.getString("ui_chosen_lang") + " : " + locale.getDisplayLanguage() + "\n");
            } catch (Exception e) {
                locale = Locale.forLanguageTag(lang);
                bundle = ResourceBundle.getBundle("bundle", Locale.US);
                System.out.println(bundle.getString("ui_chosen_lang") + " : " + locale.getDisplayLanguage() + "\n");
            }
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", locale);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm", locale);
        // if (userInput.equals("+d") && userInput != null) {
        // currentDate.plusDays(1);
        // }

        for (int i = 0; i < 7; i++) {
            LocalDateTime futureDateTime = currentDateTime.plusDays(i);

            String formattedDate = dateFormat.format(futureDateTime);
            cols.add(formattedDate);
        }

        for (int i = 0; i < 24; i++) {
            LocalTime currentTime = LocalTime.of(i, 0);
            String formattedTime = timeFormat.format(currentTime);
            rows.add(formattedTime);
        }
        rows.add("All Day \nEvents");

        for (int i = 0; i <= 24; i++) {
            var row = new ArrayList<String>();
            for (int j = 0; j < 7; j++) {
                row.add("-");
            }
            listMessages.add(row);
        }

        terminalGrid.print(listMessages, rows, cols);
        System.out.println();

    }

    public static String chooseLang() {
        Scanner scan = new Scanner(System.in);
        String lang = "";
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
                String userLang = scan.next();
                lang = userLang;
                break;
            } else {
                System.out.println("Enter a valid choice!");
            }
        }
        return lang;

    }
}
