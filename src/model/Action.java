package model;

public class Action {

    private String type;
    private TaskReminder record;

    public Action(String type, TaskReminder record) {
        this.type = type;
        this.record = record;
    }

    public String getType() {
        return type;
    }

    public TaskReminder getRecord() {
        return record;
    }

}


