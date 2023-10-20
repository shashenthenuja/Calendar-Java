package edu.curtin.assignment2.coreapp;

import org.python.util.*;

public class ScriptHandler {
    public void runScript(LoaderAPI api, String pythonScript) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.set("api", api);
        interpreter.exec(pythonScript);
    }
}
