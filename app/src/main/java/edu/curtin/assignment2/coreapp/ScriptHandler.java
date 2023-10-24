package edu.curtin.assignment2.coreapp;

import org.python.util.*;

/* *******************************************************************
* File:       ScriptHandler.java
* Author:     G.G.T.Shashen
* Created:    20/10/2023
* Modified:   25/10/2023
* Desc:       Class to handle the language choice of the user
***********************************************************************/
public class ScriptHandler {

    /* run the scripts loaded from the input file using Jython */
    public void runScript(LoaderAPI api, String pythonScript) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.set("api", api);
        interpreter.exec(pythonScript);
    }
}
