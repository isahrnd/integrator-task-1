package model;

public class Action {

    private String type;
    private TaskReminder taskReminder;
    private TaskReminder original;

    public Action(String type, TaskReminder taskReminder) {
        this.type = type;
        this.taskReminder= taskReminder;
    }

    public Action(String type, TaskReminder original, TaskReminder modified) {
        this.type = type;
        this.original = original;
        this.taskReminder = modified;
    }

    public String getType() {
        return type;
    }

    public TaskReminder getTaskReminder() {
        return taskReminder;
    }

    public TaskReminder getOriginal() {
        return original;
    }
}


