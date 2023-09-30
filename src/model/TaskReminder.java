package model;
import java.util.Calendar;

public class TaskReminder {

    private String title;
    private String description;
    private Calendar dueDate;
    private int importanceLevel;
    private boolean isTask;

    public TaskReminder(String title, String description, Calendar dueDate, int importanceLevel, boolean isTask) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.importanceLevel = importanceLevel;
        this.isTask = isTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public int getImportanceLevel() {
        return importanceLevel;
    }

    public void setImportanceLevel(int importanceLevel) {
        this.importanceLevel = importanceLevel;
    }

    public boolean isTask() {
        return isTask;
    }

    public void setTask(boolean task) {
        isTask = task;
    }
}