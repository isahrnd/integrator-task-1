package model;

import Exceptions.*;

import java.util.Calendar;
public class TaskReminderController {

    private final HashTable<String, TaskReminder> taskReminderTable;
    private final Queue<String, TaskReminder> nonPriorityTasks;
    private final MinHeap priorityTasks;
    private final Stack<String, TaskReminder> actions;

    public TaskReminderController() {
        taskReminderTable = new HashTable<>();
        nonPriorityTasks = new Queue<>();
        priorityTasks = new MinHeap();
        actions = new Stack<>();
    }

    public String addElement(String id, String title, String description, String dueDateInput){
        String msg = "New reminder added!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            TaskReminder reminder = new TaskReminder(title, description, dueDate, 0, false);
            taskReminderTable.insert(id, reminder);
        } catch (DuplicatedObjectException | InvalidDateException e) {
            msg = e.getMessage();
        }
        return msg;
    }

    public String addElement(String id, String title, String description, String dueDateInput, boolean isPriority, int importance){
        String msg = "New task added!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            TaskReminder task = new TaskReminder(title, description, dueDate, importance, true);
            taskReminderTable.insert(id, task);
            if (!isPriority){
                nonPriorityTasks.enqueue(id, task);
            } else {
                priorityTasks.insert(task);
            }
        } catch (DuplicatedObjectException | InvalidDateException e){
            msg = e.getMessage();
        }
        return msg;
    }

    public String deleteElement(String id){
        String msg = "Deleted reminder!";
        if (!taskReminderTable.delete(id)){
            msg = "Error: The reminder was not found.";
        }
        return msg;
    }

    private Calendar validateDueDate(String dueDateInput) throws InvalidDateException {
        String[] parts = dueDateInput.split("/");
        if (parts.length == 3) {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            Calendar currentDate = Calendar.getInstance();
            if (month < 1 || month > 12) {
                throw new InvalidDateException("Error: The month must be in the range 1 to 12.");
            }
            int maxDaysInMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (day < 1 || day > maxDaysInMonth) {
                throw new InvalidDateException("Error: The day is not valid for the specified month and year.");
            }
            Calendar dueDate = Calendar.getInstance();
            dueDate.set(year, month - 1, day);
            if (dueDate.before(currentDate)) {
                throw new InvalidDateException("Error: The date cannot be earlier than the current date.");
            }
            return dueDate;
        } else {
            throw new InvalidDateException("Error: Invalid date format. Use dd/mm/yyyy.");
        }
    }


}
