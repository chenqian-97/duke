import java.util.Scanner;

public class Qian {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;
    public static String line = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.println(" Hello! I'm Qian");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        while (true) {
            String input = scanner.nextLine().trim();

            try {
                if (input.equals("bye")) {
                    System.out.println(line);
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println(line);
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
                } else {
                    throw new QianException("Hmmm... I don't understand that command.");
                }
            } catch (QianException e) {
                System.out.println(line);
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println(line);
            }

        }

        scanner.close();
    }

    private static void printTasks() {
        System.out.println(line);
        if (taskCount == 0) {
            System.out.println(" No tasks yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println(" " + (i + 1) + ". " + tasks[i]);
            }
        }
        System.out.println(line);
    }

    private static void handleMark(String input) throws QianException {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
            if (taskNumber < 0 || taskNumber >= taskCount) {
                throw new QianException("That task number is invalid!");
            }
            tasks[taskNumber].markAsDone();
            System.out.println(line);
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks[taskNumber]);
            System.out.println(line);
        } catch (NumberFormatException e) {
            System.out.println(" Please enter a valid number after 'mark'.");
        }
    }

    private static void handleUnmark(String input) throws QianException {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
            if (taskNumber < 0 || taskNumber >= taskCount) {
                throw new QianException("That task number is invalid!");
            }
            tasks[taskNumber].markAsNotDone();
            System.out.println(line);
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println(" " + tasks[taskNumber]);
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
        tasks[taskCount] = new Todo(desc);
        taskCount++;
        printAddedTask(tasks[taskCount - 1]);
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
        tasks[taskCount] = new Deadline(description, by);
        taskCount++;
        printAddedTask(tasks[taskCount - 1]);
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
        tasks[taskCount] = new Event(description, from, to);
        taskCount++;
        printAddedTask(tasks[taskCount - 1]);
    }

    private static void printAddedTask(Task task) {
        System.out.println(line);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(line);
    }

}
