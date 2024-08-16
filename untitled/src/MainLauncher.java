import javax.swing.*;

public class MainLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManager taskManager = new TaskManager();
            taskManager.setVisible(true);
        });
    }
}