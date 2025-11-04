package chatbot;

import java.util.ArrayList;
import chatbot.tasks.*;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int index) throws QianException {
        if (index < 0 || index >= tasks.size())
            throw new QianException("That task number doesn't exist!");
        Task removed = tasks.remove(index);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    public void markTask(int index) throws QianException {
        if (index < 0 || index >= tasks.size())
            throw new QianException("That task number doesn't exist!");
        Task t = tasks.get(index);
        t.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + t);
    }

    public void unmarkTask(int index) throws QianException {
        if (index < 0 || index >= tasks.size())
            throw new QianException("That task number doesn't exist!");
        Task t = tasks.get(index);
        t.markAsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + t);
    }

    public void printTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your list is empty!");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
    }

    public Task getLastTask() {
        return tasks.get(tasks.size() - 1);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matching = new ArrayList<>();
        for (Task t : tasks) {
            if (t.description.toLowerCase().contains(keyword.toLowerCase())) {
                matching.add(t);
            }
        }
        return matching;
    }
}