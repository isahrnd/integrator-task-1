package model;

public class Action {

    private String type;
    private TaskReminder record;
    private TaskReminder target;

    public Action(String type, TaskReminder record) {
        this.type = type;
        this.record = record;
    }

    public Action(String type, TaskReminder record, TaskReminder target) {
        this.type = type;
        this.record = record;
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public TaskReminder getRecord() {
        return record;
    }

    public TaskReminder getTarget() {return target; }

}


