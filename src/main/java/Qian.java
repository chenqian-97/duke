import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;


public class Qian {
    private static ArrayList<Task> tasks = new ArrayList<>();
    public static String line = "____________________________________________________________";
    private static Storage storage = new Storage("./data/duke.txt");

    public static void main(String[] args) {

        System.out.println(line);
        System.out.println(" Hello! I'm Qian");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        try {
            tasks = storage.load();
            if (tasks.isEmpty()) {
                System.out.println("Starting fresh — no saved tasks yet!");
            } else {
                System.out.println("Loaded " + tasks.size() + " task(s) from storage.");
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        System.out.println(line);
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            String input = scanner.nextLine().trim();

            try {
                if (input.equals("bye")) {
                    System.out.println(" Bye. Hope to see you again soon!");
                    isRunning = false;
                    break;
                } else if (input.equals("list")) {
                    printTasks();
                } else if (input.startsWith("mark")) {
                    handleMark(input);
                } else if (input.startsWith("unmark")) {
                    handleUnmark(input);
                } else if (input.startsWith("todo")) {
                    handleTodo(input);
                } else if (input.startsWith("deadline")) {
                    handleDeadline(input);
                } else if (input.startsWith("event")) {
                    handleEvent(input);
                } else if (input.startsWith("delete ")) {
                    handleDelete(input);
                } else {
                    throw new QianException("Hmmm... I don't understand that command.");
                }

                storage.save(tasks);
            } catch (QianException e) {
                System.out.println(line);
                System.out.println(" OOPS!!! " + e.getMessage());
            } catch (IOException e) {
                System.out.println(line);
                System.out.println("Error saving data: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(line);
                System.out.println("Something went wrong: " + e.getMessage());
            }

            System.out.println(line);
        }

        scanner.close();
    }

    private static void handleDelete(String input) throws QianException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new QianException("Please specify which task number to delete!");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new QianException("The task number must be a valid integer!");
        }

        if (taskNumber < 0 || taskNumber >= tasks.size()) {
            throw new QianException("That task number doesn't exist!");
        }

        Task removedTask = tasks.remove(taskNumber);

        System.out.println(line);
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removedTask);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    private static int parseTaskNumber(String input) throws QianException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new QianException("Please specify a task number!");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new QianException("Task number must be an integer!");
        }
        if (taskNumber < 0 || taskNumber >= tasks.size()) {
            throw new QianException("That task number is invalid!");
        }
        return taskNumber;
    }

    private static void printTasks() {
        System.out.println(line);
        if (tasks.isEmpty()) {
            System.out.println(" Your list is empty! ");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println(line);
    }

    private static void handleMark(String input) throws QianException {
        try {
            int taskNumber = parseTaskNumber(input);
            Task task = tasks.get(taskNumber);
            task.markAsDone();
            System.out.println(line);
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + task);
            System.out.println(line);
        } catch (NumberFormatException e) {
            System.out.println(" Please enter a valid number after 'mark'.");
        }
    }

    private static void handleUnmark(String input) throws QianException {
        try {
            int taskNumber = parseTaskNumber(input);
            Task task = tasks.get(taskNumber);
            task.markAsNotDone();
            System.out.println(line);
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println(" " + task);
            System.out.println(line);
        } catch (NumberFormatException e) {
            System.out.println(" Please enter a valid number after 'unmark'.");
        }
    }

    private static void handleTodo(String input) throws QianException {
        String desc = input.replaceFirst("todo", "").trim();
        if (desc.isEmpty()) {
            throw new QianException("Please enter the description of a todo task.");
        }
        tasks.add(new Todo(desc));
        printAddedTask(tasks.get(tasks.size() - 1));
    }

    private static void handleDeadline(String input) throws QianException {
        if (!input.contains("/by")) {
            throw new QianException("A deadline must have a /by part!");
        }
        String[] parts = input.split("/by", 2);
        String description = parts[0].replaceFirst("deadline", "").trim();
        String by = parts[1].trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new QianException("The description or /by part of a deadline cannot be empty!");
        }
        tasks.add(new Deadline(description, by));
        printAddedTask(tasks.get(tasks.size() - 1));
    }

    private static void handleEvent(String input) throws QianException {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new QianException("An event must have /from and /to parts!");
        }
        String[] firstSplit = input.split("/from", 2);
        String description = firstSplit[0].replaceFirst("event", "").trim();
        String[] secondSplit = firstSplit[1].split("/to", 2);
        String from = secondSplit[0].trim();
        String to = secondSplit[1].trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new QianException("The description, /from, or /to part of an event cannot be empty!");
        }
        tasks.add(new Event(description, from, to));
        printAddedTask(tasks.get(tasks.size() - 1));
    }

    private static void printAddedTask(Task task) {
        System.out.println(line);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

}
