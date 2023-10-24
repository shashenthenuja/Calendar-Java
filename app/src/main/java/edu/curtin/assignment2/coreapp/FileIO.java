package edu.curtin.assignment2.coreapp;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileIO {
    private String fileName;
    private List<Map<String, String>> data = new ArrayList<>();

    public FileIO(String[] args) {
        this.fileName = args[0];
    }

    public void parseFile() {
        String content = "";
        if (fileName.contains("utf8")) {
            content = convert8();
            parseData(content);
        } else if (fileName.contains("utf16")) {
            content = convert16();
            parseData(content);
        } else if (fileName.contains("utf32")) {
            content = convert32();
            parseData(content);
        } else {
            content = convert8();
            parseData(content);
        }
    }

    @SuppressWarnings("static-access")
    public void parseData(String content) {
        DSLParser parser = new DSLParser(new StringReader(content));
        try {
            data = parser.file();
        } catch (Exception e) {
            System.out.println("Failed to parse data : " + e.getLocalizedMessage());
        }
    }

    public String convert8() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convert16() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_16);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convert32() {
        Path path = Paths.get(fileName);

        try (FileInputStream fileInput = new FileInputStream(path.toFile());
                InputStreamReader inputStream = new InputStreamReader(fileInput, Charset.forName("UTF-32"));
                BufferedReader bReader = new BufferedReader(inputStream)) {

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bReader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            return content.toString();
        } catch (IOException e) {
            System.out.println("Failed to read!");
        }
        return null;
    }

    public List<Map<String, String>> getData() {
        return data;
    }
}
