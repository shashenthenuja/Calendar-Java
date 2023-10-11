package edu.curtin.assignment2;

import java.util.*;


public class App {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("\033[H\033[2J");
            cal();
            System.out.print("\n> ");
            String userInput = scan.next();
        }
    }

    public static void cal() {
        // Initialising
        var terminalGrid = TerminalGrid.create();

        var listMessages = new ArrayList<List<String>>();
        var rows = new ArrayList<String>();
        var cols = new ArrayList<String>();
        var row1 = new ArrayList<String>();
        var row2 = new ArrayList<String>();
        var row3 = new ArrayList<String>();
        rows.add("00:00");
        rows.add("01:00");
        rows.add("02:00");
        cols.add("Oct 11");
        cols.add("Oct 12");
        cols.add("Oct 13");
        row1.add("one");
        row1.add("two");
        row1.add("three");
        row2.add("four");
        row2.add("five");
        row2.add("six");
        row3.add("seven");
        row3.add("eight");
        row3.add("nine");
        listMessages.add(row1);
        listMessages.add(row2);
        listMessages.add(row3);
        terminalGrid.print(listMessages, rows, cols);
        System.out.println();

    }
}
