// Author : Mayank kumar and Kultaran Singh
// Name: MainLauncher.java
// Version : 1.0
// Date: 10/08/2024
// Description : This code file create task manager app using Java Swing UI

import javax.swing.*;

public class MainLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManager taskManager = new TaskManager();
            taskManager.setVisible(true);
        });
    }
}