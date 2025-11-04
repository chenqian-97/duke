import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return tasks; // return empty list on first startup
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" \\| ");
            if (parts.length < 3) continue; // skip invalid lines

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];
            Task t = null;

            switch (type) {
                case "T":
                    t = new Todo(description);
                    break;
                case "D":
                    if (parts.length >= 4) {
                        t = new Deadline(description, parts[3]);
                    }
                    break;
                case "E":
                    if (parts.length >= 5) {
                        t = new Event(description, parts[3], parts[4]);
                    }
                    break;
            }

            if (t != null && isDone) {
                t.markAsDone();
            }

            if (t != null) {
                tasks.add(t);
            }
        }
        scanner.close();
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (Task t : tasks) {
            String line = "";

            if (t instanceof Todo) {
                line = "T | " + (t.isDone ? "1" : "0") + " | " + t.description;
            } else if (t instanceof Deadline) {
                Deadline d = (Deadline) t;
                line = "D | " + (d.isDone ? "1" : "0") + " | " + d.description + " | " + d.getBy();
            } else if (t instanceof Event) {
                Event e = (Event) t;
                line = "E | " + (e.isDone ? "1" : "0") + " | " + e.description + " | " + e.from + " | " + e.to;
            }

            fw.write(line + System.lineSeparator());
        }
        fw.close();
    }
}
