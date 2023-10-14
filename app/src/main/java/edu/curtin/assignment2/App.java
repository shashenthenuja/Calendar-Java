package edu.curtin.assignment2;

import java.time.*;
import java.util.*;

public class App {
    static String userInput;
    static ResourceBundle bundle;

    public static void main(String[] args) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Control control = new Control(currentDateTime);
        control.start();
    }
}
