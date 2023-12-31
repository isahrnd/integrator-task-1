package model;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class TaskReminder {

    private final String id;
    private String title;
    private String description;
    private Calendar dueDate;
    private int importanceLevel;
    private boolean isTask;

    public TaskReminder(String id, String title, String description, Calendar dueDate, int importanceLevel, boolean isTask) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.importanceLevel = importanceLevel;
        this.isTask = isTask;
    }

    public TaskReminder(TaskReminder original) {
        this.id = original.getId();
        this.title = original.getTitle();
        this.description = original.getDescription();
        this.dueDate = (original.getDueDate() != null) ? (Calendar) original.getDueDate().clone() : null;
        this.importanceLevel = original.getImportanceLevel();
        this.isTask = original.isTask();
    }

    public String getId() {return id; }

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

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(dueDate.getTime());
        return "Id: " + id + "\n" +
                "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Due date: " + date + "\n" +
                "Importance: " + importanceLevel;
    }
}