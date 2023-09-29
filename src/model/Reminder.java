package model;

import java.util.Calendar;

public class Reminder extends TaskReminderBase{
    public Reminder(String title, String description, Calendar dueDate) {
        super(title, description, dueDate);
    }
}
