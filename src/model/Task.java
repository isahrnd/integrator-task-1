package model;
import java.util.Calendar;

public class Task extends TaskReminderBase{

    private int importance;

    public Task(String title, String description, Calendar dueDate, int importance) {
        super(title, description, dueDate);
        this.importance = importance;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
