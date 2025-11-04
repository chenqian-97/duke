package chatbot;

public class Parser {
    public String[] parse(String input) {
        return input.split(" ", 2);
    }

    public String getDescription(String input, String commandWord) throws QianException {
        String desc = input.replaceFirst(commandWord, "").trim();
        if (desc.isEmpty()) {
            throw new QianException("The description of a " + commandWord + " cannot be empty.");
        }
        return desc;
    }

    public int getTaskNumber(String input) throws QianException {
        String[] parts = input.split(" ");
        if (parts.length < 2) throw new QianException("Please specify a task number!");
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new QianException("Task number must be an integer!");
        }
    }

    public String[] parseDeadline(String input) throws QianException {
        if (!input.contains("/by")) throw new QianException("A deadline must have a /by part!");
        String[] parts = input.split("/by", 2);
        String desc = parts[0].replaceFirst("deadline", "").trim();
        String by = parts[1].trim();
        if (desc.isEmpty() || by.isEmpty()) throw new QianException("Deadline description or /by cannot be empty!");
        return new String[]{desc, by};
    }

    public String[] parseEvent(String input) throws QianException {
        if (!input.contains("/from") || !input.contains("/to"))
            throw new QianException("An event must have /from and /to parts!");
        String[] first = input.split("/from", 2);
        String desc = first[0].replaceFirst("event", "").trim();
        String[] time = first[1].split("/to", 2);
        if (desc.isEmpty() || time[0].trim().isEmpty() || time[1].trim().isEmpty())
            throw new QianException("The description, /from, or /to part cannot be empty!");
        return new String[]{desc, time[0].trim(), time[1].trim()};
    }

    public static String parseKeyword(String input) throws QianException {
        String[] parts = input.trim().split(" ", 2);
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new QianException("Please provide a keyword to search for!");
        }
        return parts[1];
    }
}