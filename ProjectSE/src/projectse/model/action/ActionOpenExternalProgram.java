/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import projectse.controller.MyProjectSEViewController;

/**
 *
 * @author group07
 */
public class ActionOpenExternalProgram implements Action, Serializable{
    private String args;
    private String token[];
    private File file;
    private int runExitCode;
    private int compileExitCode;

    public ActionOpenExternalProgram(String args, File file) {
        this.args = args;
        this.token = args.split(";"); //Spli the arguments when there is a ;
        this.file = file;
    }
    
    @Override
    public String getAction() {
        return "Open External Program -> " + args;
    }

    @Override
    public void executeAction() {
        String fileName = file.getName();

        try {
            if (fileName.endsWith(".java")) {
                executeJavaFile();
            } else {
                System.out.println("Tipo di file non supportato.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeJavaFile() throws IOException, InterruptedException {
        // Compile the Java file 
        Process compileProcess = new ProcessBuilder("javac", file.getAbsolutePath()).start();
        compileExitCode = compileProcess.waitFor();

        if (compileExitCode == 0) {
            // Calculate the classpath and the class name
            String classPath = file.getParent(); // Get the directory of the file
            String className = file.getName().replace(".java", "");
                    
            List<String> command = new ArrayList<>();
            command.add("java");
            command.add("-cp");
            command.add(classPath); // Set the classpath
            command.add(className);
            command.addAll(Arrays.asList(token));

            // Execute the Java file
            Process runProcess = new ProcessBuilder(command).start();

            String msg = printProcessOutput(runProcess);
            printProcessError(runProcess);

            runExitCode = runProcess.waitFor();
            System.out.println("Processo Java terminato con codice di uscita: " + runExitCode);
            MyProjectSEViewController.showSuccessPopup("Java process ended with exit code: " + runExitCode, msg, false);
        } else {
            System.out.println("Error during the compilation of the Java file");
        }
    }

    private String printProcessOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String s = "";
        while ((line = reader.readLine()) != null) {
            s = s + line;
            System.out.println(line);
        }
        return s;
    }
    
    private void printProcessError(Process process) throws IOException {
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = errorReader.readLine()) != null) {
            System.err.println(line);
        }
    }
    
    public int getLastExitCode() {
        return runExitCode;
    }
    
    public int getCompileExitCode() {
        return compileExitCode;
    }
}