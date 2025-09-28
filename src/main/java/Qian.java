import java.util.Scanner;

public class Qian {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "____________________________________________________________";
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(line);
        System.out.println(" Hello! I'm Qian");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else if (input.equalsIgnoreCase("list")) {
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
            } else if (input.startsWith("mark ")) {
                try {
                    int taskNumber = Integer.parseInt(input.substring(5)) - 1;
                    if (taskNumber >= 0 && taskNumber < taskCount) {
                        tasks[taskNumber].markAsDone();
                        System.out.println(line);
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[taskNumber]);
                        System.out.println(line);
                    } else {
                        System.out.println(" Invalid task number!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please enter a valid number after 'mark'.");
                }

            } else if (input.startsWith("unmark ")) {
                try {
                    int taskNumber = Integer.parseInt(input.substring(7)) - 1;
                    if (taskNumber >= 0 && taskNumber < taskCount) {
                        tasks[taskNumber].markAsNotDone();
                        System.out.println(line);
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println(" " + tasks[taskNumber]);
                        System.out.println(line);
                    } else {
                        System.out.println(" Invalid task number!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please enter a valid number after 'unmark'.");
                }

            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                Task t = new Todo(desc);
                tasks[taskCount++] = t;
                System.out.println(line);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + t);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);

            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split("/by", 2);
                String desc = parts[0].trim();
                String by = (parts.length > 1) ? parts[1].trim() : "unspecified";
                Task t = new Deadline(desc, by);
                tasks[taskCount++] = t;
                System.out.println(line);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + t);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);

            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split("/from|/to");
                if (parts.length < 3) {
                    System.out.println(" Event format: event <desc> /from <start> /to <end>");
                    continue;
                }
                String desc = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                Task t = new Event(desc, from, to);
                tasks[taskCount++] = t;
                System.out.println(line);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + t);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);

            } else {
                System.out.println(" Unknown command: " + input);
            }

        }

        scanner.close();
    }
}
