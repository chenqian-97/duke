import java.util.Scanner;
import java.util.Random;

public class Qian {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String line = "____________________________________________________________";
        String[] task = new String[100];
        int taskCount = 0;

        System.out.println(line);
        System.out.println(" Hello! I'm Qian");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        String[] responses = {
                "Interesting!",
                "Haha, got it!",
                "Nice.",
                "Added successfully!"
        };

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
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + ". " + task[i]);
                    }
                }
                System.out.println(line);
            } else {
                // 添加任务
                if (taskCount < task.length) {
                    task[taskCount] = input;
                    taskCount++;
                    System.out.println(line);
                    String response = responses[random.nextInt(responses.length)];
                    System.out.println(" added: " + input + " (" + response + ")");
                    System.out.println(line);
                } else {
                    System.out.println(line);
                    System.out.println(" Task list is full! Cannot add more.");
                    System.out.println(line);
                }
            }

//            if (input.equalsIgnoreCase("hello")) {
//                System.out.println(line);
//                System.out.println(" Hello there! Nice to chat with you!");
//                System.out.println(line);
//                continue;
//            } else if (input.equalsIgnoreCase("how are you")) {
//                System.out.println(line);
//                System.out.println(" I'm just a chatbot, but I'm feeling great!");
//                System.out.println(line);
//                continue;
//            }
//
//            String response = responses[random.nextInt(responses.length)];
//            System.out.println(line);
//            System.out.println(response + (response.equals("You said: ") ? input : ""));
//            System.out.println(line);
        }

        scanner.close();
    }
}
