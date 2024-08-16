// Author : Mayank kumar and Kultaran Singh
// Name: Task.java
// Version : 1.0
// Date: 10/08/2024
// Description : This code file create task manager app using Java Swing UI

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private static int idCounter = 1;
    private int taskID;
    private String name;
    private boolean isComplete;
    private Date deadline;

    public Task(String name, boolean isComplete, Date deadline) {
        this.taskID = idCounter++;
        this.name = name;
        this.isComplete = isComplete;
        this.deadline = deadline;
    }

    public int getTaskID() {
        return taskID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return taskID + "," + name + "," + isComplete + "," + dateFormat.format(deadline);
    }

    public static Task fromString(String line) {
        String[] parts = line.split(",");
        int taskID = Integer.parseInt(parts[0]);
        String name = parts[1];
        boolean isComplete = Boolean.parseBoolean(parts[2]);
        Date deadline = null;
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Task task = new Task(name, isComplete, deadline);
        task.taskID = taskID;
        return task;
    }
}
