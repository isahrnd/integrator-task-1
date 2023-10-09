package model;

public class Action {

    private String type;
    private TaskReminder record;
    private String targetHashID;
    private String targetQueueID;

    public Action(String type, TaskReminder record) {
        this.type = type;
        this.record = record;
    }

    public Action(String type, TaskReminder record, String targetHashID) {
        this.type = type;
        this.record = record;
        this.targetHashID= targetHashID;
    }

    public Action(String type, TaskReminder record, String targetHashID, String targetQueueID) {
        this.type = type;
        this.record = record;
        this.targetHashID = targetHashID;
        this.targetQueueID = targetQueueID;
    }

    public String getType() {
        return type;
    }

    public TaskReminder getRecord() {
        return record;
    }

    public String getTargetHashID() {
        return targetHashID;
    }

    public String getTargetQueueID() {
        return targetQueueID;
    }
}


