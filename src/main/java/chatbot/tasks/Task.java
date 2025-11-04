package chatbot.tasks;

import java.time.LocalDateTime;
import java.util.Optional;

public abstract class Task {
    public String description;
    public boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public Optional<LocalDateTime[]> getBusyPeriod() {
        return Optional.empty(); // default: none
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}