package chatbot;

public class Ui {
    private static final String LINE = "____________________________________________________________";

    public void showLine() {
        System.out.println(LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Qian 🌸");
        System.out.println(" What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }

    public void showMessage(String msg) {
        showLine();
        System.out.println(" " + msg);
        showLine();
    }

    public void showError(String msg) {
        showLine();
        System.out.println(" ⚠️ OOPS!!! " + msg);
        showLine();
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks from file. Starting with an empty list.");
    }

    public void showAddConfirmation(TaskList tasks) {
        chatbot.tasks.Task t = tasks.getLastTask();
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        showLine();
    }
}