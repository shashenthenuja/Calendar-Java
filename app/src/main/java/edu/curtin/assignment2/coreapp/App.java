package edu.curtin.assignment2.coreapp;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.curtin.assignment2.pluginapi.API;
import edu.curtin.assignment2.pluginapi.Event;

public class App {
    static ResourceBundle bundle;
    static Locale locale;

    public static void main(String[] args) {
        List<Event> eventList = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Language lang = new Language();
        bundle = lang.getBundle();
        locale = lang.getLocale();
        Control control = new Control(currentDateTime, eventList, bundle, locale);
        pluginStart(eventList);
        Map<String, String> args3 = new HashMap<>();
        args3.put("query", "test day");
        LoaderAPI notify = new LoaderAPI(args3, eventList, bundle);
        runScript(eventList, args);
        control.start(notify);
    }

    public static void pluginStart(List<Event> eventList) {

        var pluginList = new ArrayList<API>();

        Map<String, String> args = new HashMap<>();
        args.put("title", "Assignment Day");
        args.put("startDate", "2023-10-23");
        // args.put("startTime", "01:00:00");
        // args.put("duration", "60");
        args.put("repeat", "0");

        Map<String, String> args2 = new HashMap<>();
        args2.put("title", "New Day");
        args2.put("startDate", "2023-10-21");
        args2.put("startTime", "02:14:00");
        args2.put("duration", "60");
        args2.put("repeat", "0");

        Map<String, String> args3 = new HashMap<>();
        args3.put("query", "assi");

        LoaderAPI pluginAPI = new LoaderAPI(args, eventList, bundle);

        LoaderAPI pluginAPI2 = new LoaderAPI(args2, eventList, bundle);

        pluginList.add(new PluginLoader().loadPlugin("edu.curtin.calplugins.Repeat", pluginAPI));
        pluginList.add(new PluginLoader().loadPlugin("edu.curtin.calplugins.Repeat", pluginAPI2));

    }

    public static void runScript(List<Event> eventList, String[] args) {
        String filePath = args[0];
        String script = "";
        
        try {
            // Create a FileReader to read the file
            FileReader fileReader = new FileReader(filePath);
            
            // Create a BufferedReader to efficiently read the file
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            // Initialize a string to store the file contents
            StringBuilder fileContents = new StringBuilder();
            String line;
            
            // Read each line of the file and append it to the string
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.append(line).append("\n");
            }
            
            // Close the BufferedReader and FileReader
            bufferedReader.close();
            fileReader.close();
            
            // Convert the StringBuilder to a String
            String fileContentString = fileContents.toString();
            script = fileContentString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScriptHandler scriptLoad = new ScriptHandler();
        LoaderAPI api = new LoaderAPI(eventList);
        scriptLoad.runScript(api, script);
    }
}
