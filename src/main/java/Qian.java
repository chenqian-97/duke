import java.util.Scanner;
import java.util.Random;

public class Qian {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String line = "____________________________________________________________";

        System.out.println(line);
        System.out.println(" Hello! I'm Qian");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        String[] responses = {
                "Interesting!",
                "Haha, got it!",
                "Oh, really?",
                "You said: "
        };

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            if (input.equalsIgnoreCase("hello")) {
                System.out.println(line);
                System.out.println(" Hello there! Nice to chat with you!");
                System.out.println(line);
                continue;
            } else if (input.equalsIgnoreCase("how are you")) {
                System.out.println(line);
                System.out.println(" I'm just a chatbot, but I'm feeling great!");
                System.out.println(line);
                continue;
            }

            String response = responses[random.nextInt(responses.length)];
            System.out.println(line);
            System.out.println(response + (response.equals("You said: ") ? input : ""));
            System.out.println(line);
        }

        scanner.close();
    }
}
