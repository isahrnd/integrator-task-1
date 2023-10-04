package model;

public class Action {

    private String type;
    private TaskReminder record;
    private TaskReminder targetHashElement;
    private TaskReminder targetQueueElement;

    public Action(String type, TaskReminder record) {
        this.type = type;
        this.record = record;
    }

    public Action(String type, TaskReminder record, TaskReminder targetHashElement, TaskReminder targetQueueElement) {
        this.type = type;
        this.record = record;
        this.targetHashElement = targetHashElement;
        this.targetQueueElement = targetQueueElement;
    }

    public String getType() {
        return type;
    }

    public TaskReminder getRecord() {
        return record;
    }

    public TaskReminder getTargetHashElement() {return targetHashElement; }

    public TaskReminder getTargetQueueElement() {return  targetQueueElement; }

}


