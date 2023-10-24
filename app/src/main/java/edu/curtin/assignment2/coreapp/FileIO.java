package edu.curtin.assignment2.coreapp;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/* *******************************************************************
* File:       FileIO.java
* Author:     G.G.T.Shashen
* Created:    24/10/2023
* Modified:   25/10/2023
* Desc:       Class to handle and read the input file for UTF8, UTF16,
              UTF32 encodings accordingly using the filename
***********************************************************************/
public class FileIO {
    private String fileName;
    private List<Map<String, String>> data = new ArrayList<>();

    public FileIO(String[] args) {
        this.fileName = args[0];
    }

    /* validate the file name and parse according to the encoding format */
    public void parseFile() throws ParseException, IOException {
        if (fileName.contains("utf8")) {
            String content = convert8();
            parseData(content);
        } else if (fileName.contains("utf16")) {
            String content = convert16();
            parseData(content);
        } else if (fileName.contains("utf32")) {
            String content = convert32();
            parseData(content);
        } else {
            String content = convert8();
            parseData(content);
        }
    }

    /*
     * parse the domain specific language file. Suppressed DSL parse to handle pmd
     */
    @SuppressWarnings("static-access")
    public void parseData(String content) throws ParseException {
        DSLParser parser = new DSLParser(new StringReader(content));
        data = parser.file();
    }

    /* read and return the content of the input file using UTF8 */
    public String convert8() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        return content;
    }

    /* read and return the content of the input file using UTF16 */
    public String convert16() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_16);
        return content;
    }

    /* read and return the content of the input file using UTF32 */
    public String convert32() {
        Path path = Paths.get(fileName);
        try (
                FileInputStream fileInput = new FileInputStream(path.toFile());
                InputStreamReader inputStream = new InputStreamReader(fileInput, Charset.forName("UTF-32"));
                BufferedReader bReader = new BufferedReader(inputStream);) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bReader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            return content.toString();
        } catch (IOException e) {
            System.out.println("Failed to parse file!");
        }
        return null;
    }

    /* return the parsed data list to load them */
    public List<Map<String, String>> getData() {
        return data;
    }
}
