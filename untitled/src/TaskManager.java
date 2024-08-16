// Author : Mayank kumar and Kultaran Singh
// Name: TaskManager.java
// Version : 1.0
// Date: 10/08/2024
// Description : This code file create task manager app using Java Swing UI


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskManager extends JFrame {
    private ArrayList<Task> tasks = new ArrayList<>();
    private int currentTaskIndex = 0;

    private JTextField taskIDField;
    private JTextField nameField;
    private JCheckBox completeCheckBox;
    private JTextField deadlineField;

    private JButton forwardButton;
    private JButton backButton;
    private JButton saveTaskButton;
    private JButton addNewTaskButton;
    private JButton saveListButton;

    public TaskManager() {
        // Load tasks from file
        loadTasks();

        // Setup UI
        setTitle("Task Manager Application");
        setLayout(new BorderLayout(10, 10)); // Added padding

        // Top Area: Navigation
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Added padding
        taskIDField = new JTextField(5);
        taskIDField.setEditable(false);
        forwardButton = new JButton("Forward");
        backButton = new JButton("Back");

        // Set mnemonics for navigation buttons
        forwardButton.setMnemonic('F');
        backButton.setMnemonic('B');

        // Set tooltips for navigation buttons
        forwardButton.setToolTipText("Go to the next task (Alt+F)");
        backButton.setToolTipText("Go to the previous task (Alt+B)");

        topPanel.add(taskIDField);
        topPanel.add(backButton);
        topPanel.add(forwardButton);
        add(topPanel, BorderLayout.NORTH);

        // Bottom Area: Task Details
        JPanel bottomPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // Added padding between fields
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Added padding
        bottomPanel.add(new JLabel("Task Name:"));
        nameField = new JTextField();
        nameField.setToolTipText("Enter the name of the task here");
        bottomPanel.add(nameField);

        bottomPanel.add(new JLabel("Completed:"));
        completeCheckBox = new JCheckBox();

        // Set mnemonic and tooltip for the checkbox
        completeCheckBox.setMnemonic('C');
        completeCheckBox.setToolTipText("Mark the task as completed (Alt+C)");

        bottomPanel.add(completeCheckBox);

        bottomPanel.add(new JLabel("Deadline (yyyy-mm-dd):"));
        deadlineField = new JTextField();
        deadlineField.setToolTipText("Enter the deadline for the task in yyyy-mm-dd format");
        bottomPanel.add(deadlineField);

        saveTaskButton = new JButton("Save Task");
        addNewTaskButton = new JButton("Add New Task");

        // Set mnemonics and tooltips for the buttons
        saveTaskButton.setMnemonic('S');
        saveTaskButton.setToolTipText("Save the current task (Alt+S)");

        addNewTaskButton.setMnemonic('A');
        addNewTaskButton.setToolTipText("Add a new task (Alt+A)");

        bottomPanel.add(saveTaskButton);
        bottomPanel.add(addNewTaskButton);
        add(bottomPanel, BorderLayout.CENTER);

        // Save List Button
        saveListButton = new JButton("Save List");
        saveListButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Added padding

        // Set mnemonic and tooltip for the Save List button
        saveListButton.setMnemonic('L');
        saveListButton.setToolTipText("Save the list of tasks to a file (Alt+L)");

        add(saveListButton, BorderLayout.SOUTH);

        // Set up event listeners
        forwardButton.addActionListener(e -> {
            if (currentTaskIndex < tasks.size() - 1) {
                currentTaskIndex++;
                updateTaskDisplay();
            }
        });

        backButton.addActionListener(e -> {
            if (currentTaskIndex > 0) {
                currentTaskIndex--;
                updateTaskDisplay();
            }
        });

        saveTaskButton.addActionListener(e -> saveCurrentTask());

        addNewTaskButton.addActionListener(e -> addNewTask());

        saveListButton.addActionListener(e -> saveTasks());

        // Update task display
        updateTaskDisplay();
        setButtonStates();

        // Frame settings
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void updateTaskDisplay() {
        if (tasks.isEmpty()) return;

        Task currentTask = tasks.get(currentTaskIndex);
        taskIDField.setText(String.valueOf(currentTask.getTaskID()));
        nameField.setText(currentTask.getName());
        completeCheckBox.setSelected(currentTask.isComplete());
        deadlineField.setText(new SimpleDateFormat("yyyy-MM-dd").format(currentTask.getDeadline()));

        setButtonStates();
    }

    private void setButtonStates() {
        backButton.setEnabled(currentTaskIndex > 0);
        forwardButton.setEnabled(currentTaskIndex < tasks.size() - 1);
    }

    private void saveCurrentTask() {
        if (tasks.isEmpty()) return;

        Task currentTask = tasks.get(currentTaskIndex);
        currentTask.setName(nameField.getText());
        currentTask.setComplete(completeCheckBox.isSelected());
        try {
            Date deadline = new SimpleDateFormat("yyyy-MM-dd").parse(deadlineField.getText());
            currentTask.setDeadline(deadline);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-mm-dd.");
        }
        JOptionPane.showMessageDialog(this, "Task saved.");
    }

    private void addNewTask() {
        Task newTask = new Task("New Task", false, new Date());
        tasks.add(newTask);
        currentTaskIndex = tasks.size() - 1;
        updateTaskDisplay();
    }

    private void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromString(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load tasks.");
        }
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Task list saved.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save tasks.");
        }
    }
}
