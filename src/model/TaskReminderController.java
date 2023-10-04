package model;

import Exceptions.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TaskReminderController {

    private final HashTable<String, TaskReminder> taskReminderTable;
    private final Queue<String, TaskReminder> nonPriorityTasks;
    private final MaxHeap priorityTasks;
    private final Stack<String, Action> actions;

    public TaskReminderController() {
        taskReminderTable = new HashTable<>();
        nonPriorityTasks = new Queue<>();
        priorityTasks = new MaxHeap();
        actions = new Stack<>();
    }

    public String addElement(String id, String title, String description, String dueDateInput){
        String msg = "New reminder added!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            TaskReminder reminder = new TaskReminder(id, title, description, dueDate, 0, false);
            taskReminderTable.insert(id, reminder);
            actions.push(id, new Action("Add element", reminder));
        } catch (DuplicatedObjectException | InvalidDateException e) {
            msg = e.getMessage();
        }
        return msg;
    }

    public String addElement(String id, String title, String description, String dueDateInput, boolean isPriority, int importance){
        String msg = "New task added!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            TaskReminder task = new TaskReminder(id, title, description, dueDate, importance, true);
            if (isPriority){
                priorityTasks.insert(task);
            } else {
                nonPriorityTasks.enqueue(id, task);
            }
            try {
                taskReminderTable.insert(id, task);
            }  catch (DuplicatedObjectException e){
                msg = e.getMessage();
            }
            actions.push(id, new Action("Add element", task));
        } catch (HeapSizeException |  InvalidDateException e){
            msg = e.getMessage();
        }
        return msg;
    }

    public String editElement(String id, String title, String description, String dueDateInput){
        String msg = "Reminder edited successfully!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            Node<String, TaskReminder> hashNode = taskReminderTable.search(id);
            if (hashNode == null || hashNode.getValue().isTask()){
                msg = "Error: The reminder doesn't exist.";
            } else {
                TaskReminder reminder = hashNode.getValue();
                actions.push(id, new Action("Edit element", reminder));
                reminder.setTitle(title);
                reminder.setDescription(description);
                reminder.setDueDate(dueDate);
            }
        } catch (InvalidDateException e){
            msg = e.getMessage();
        }
        return msg;
    }

    public String editElement(String id, String title, String description, String dueDateInput, boolean isPriority, int importance){
        String msg = "Task edited successfully!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            Node<String, TaskReminder> hashNode = taskReminderTable.search(id);
            if (hashNode == null || !hashNode.getValue().isTask()){
                msg = "Error: The task doesn't exist.";
            } else {
                TaskReminder task = hashNode.getValue();
                actions.push(id, new Action("Edit element", task));
                task.setTitle(title);
                task.setDescription(description);
                task.setDueDate(dueDate);
                if (task.getImportanceLevel() != 0){
                    int index = priorityTasks.searchTaskIndex(task);
                    if (isPriority){
                        TaskReminder priorityTask = priorityTasks.getHeap()[index];
                        priorityTask.setTitle(title);
                        priorityTask.setDescription(description);
                        priorityTask.setDueDate(dueDate);
                        priorityTask.setImportanceLevel(importance);
                        priorityTasks.maxHeapify(0);
                    } else {
                        priorityTasks.getHeap()[index] = priorityTasks.getHeap()[priorityTasks.getHeapSize()-1];
                        priorityTasks.getHeap()[priorityTasks.getHeapSize()-1] = null;
                        priorityTasks.setHeapSize(priorityTasks.getHeapSize()-1);
                        priorityTasks.maxHeapify(0);
                        task.setImportanceLevel(0);
                        nonPriorityTasks.enqueue(id, task);
                    }
                } else {
                   Node<String, TaskReminder> queueNode = nonPriorityTasks.search(id);
                    if (!isPriority){
                        queueNode.getValue().setTitle(title);
                        queueNode.getValue().setDescription(description);
                        queueNode.getValue().setDueDate(dueDate);
                    } else {
                        nonPriorityTasks.delete(queueNode);
                        task.setImportanceLevel(importance);
                        priorityTasks.insert(task);
                    }
                }
                task.setImportanceLevel(importance);
            }
        } catch (InvalidDateException | HeapSizeException e){
            msg = e.getMessage();
        }
        return msg;
    }

    public String deleteElement(String id){
        String msg = "Reminder removed!.";
        Node<String, TaskReminder> hashNode = taskReminderTable.search(id);
        if (hashNode != null){
            taskReminderTable.delete(id);
            Node<String, TaskReminder> targetHashNode = hashNode.getPrevious();
            TaskReminder targetHashElement = null;
            if (targetHashNode != null){
                targetHashElement = targetHashNode.getValue();
            }
            TaskReminder element = hashNode.getValue();
            Node<String, TaskReminder> queueNode = null;
            if (element.isTask()){
                if (element.getImportanceLevel() != 0){
                    int index = priorityTasks.searchTaskIndex(element);
                    priorityTasks.getHeap()[index] = priorityTasks.getHeap()[priorityTasks.getHeapSize() - 1];
                    priorityTasks.getHeap()[priorityTasks.getHeapSize() - 1] = null;
                    priorityTasks.setHeapSize(priorityTasks.getHeapSize()-1);
                    priorityTasks.maxHeapify(0);
                } else {
                    queueNode = nonPriorityTasks.search(id);
                    nonPriorityTasks.delete(queueNode);
                }
                msg = "Task removed!";
            }
            Node<String, TaskReminder> targetQueueNode = null;
            if (queueNode != null){
                targetQueueNode = queueNode.getPrevious();
            }
            TaskReminder targetQueueElement = null;
            if (targetQueueNode != null){
                targetQueueElement = targetQueueNode.getValue();
            }
            actions.push(id, new Action("Delete element", element, targetHashElement, targetQueueElement));
        } else {
            msg = "Error: The element was not found.";
        }
        return msg;
    }

    public void undoAction(){
        if (!actions.isEmpty()) {
            Action action = actions.pop();
            String actionType = action.getType();
            TaskReminder record = action.getRecord();
            String id = record.getId();
            if (actionType.equals("Add element")) {
                deleteElement(id);
            } else {
                String title = record.getTitle();
                String description = record.getDescription();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dueDate = dateFormat.format(record.getDueDate().getTime());
                int importance = record.getImportanceLevel();
                if (actionType.equals("Edit element")) {
                    if (!record.isTask()){
                        editElement(id, title, description, dueDate);
                    } else {
                        editElement(id, title, description, dueDate, importance != 0, importance);
                    }
                } else {
                    if (record.getImportanceLevel() != 0){
                        try {
                            priorityTasks.insert(record);
                        } catch (HeapSizeException ignored) {}
                    } else {
                        String targetHashID = null, targetQueueID = null;
                        if (action.getTargetHashElement() != null){
                            targetHashID = action.getTargetHashElement().getId();
                        }
                        if (action.getTargetQueueElement() != null){
                            targetQueueID = action.getTargetQueueElement().getId();
                        }
                        addAfterTarget(targetHashID, targetQueueID, record);
                    }
                }
            }
        }
    }

    public String showList(){
        if (!taskReminderTable.isEmpty()){
            StringBuilder list = new StringBuilder("PRIORITY TASKS:\n");
            TaskReminder[] heapElements = new TaskReminder[priorityTasks.getHeapSize()];
            //extract and save the maximum
            for (int i = 0; i < heapElements.length; i++){
                TaskReminder max = priorityTasks.extractMaximum();
                list.append("\n").append(max.toString()).append("\n");
                heapElements[i] = max;
            }
            //reintegrate the extracted elements into the heap.
            for (TaskReminder element : heapElements) {
                try {
                    priorityTasks.insert(element);
                } catch (HeapSizeException ignored) {
                }
            }

            list.append("\nNON PRIORITY TASKS:\n");
            Node<String, TaskReminder> currentQueueNode = nonPriorityTasks.peek();
            while (currentQueueNode != null) {
                list.append("\n").append(currentQueueNode.getValue()).append("\n");
                currentQueueNode = currentQueueNode.getNext();
            }

            list.append("\nREMINDERS:\n");
            for (int i = 0; i < taskReminderTable.getTable().length; i++){
                Node<String, TaskReminder> currentHashNode = taskReminderTable.getTable()[i];
                while (currentHashNode != null) {
                    if (!currentHashNode.getValue().isTask()) {
                        list.append("\n").append(currentHashNode.getValue()).append("\n");
                    }
                    currentHashNode = currentHashNode.getNext();
                }
            }
            return list.toString();
        }
        return "Error: there are no elements registered yet.";
    }

    private void addAfterTarget(String targetHashID, String targetQueueID, TaskReminder element){
        Node<String, TaskReminder> nodeToAdd = new Node<>(element.getId(), element);
        if (targetHashID != null){
            //Return node to hash table
            Node<String, TaskReminder> hashTarget = taskReminderTable.search(targetHashID);
            if (hashTarget.getNext() != null){
                hashTarget.getNext().setPrevious(nodeToAdd);
                nodeToAdd.setNext(hashTarget.getNext());
            }
            hashTarget.setNext(nodeToAdd);
            nodeToAdd.setPrevious(hashTarget);
        } else {
            try {
                taskReminderTable.insert(element.getId(), element);
            } catch (DuplicatedObjectException ignored) {}
        }
        if (targetQueueID != null){
            //Return node to queue
            Node<String, TaskReminder> queueTarget = nonPriorityTasks.search(targetQueueID);
            if (queueTarget.getNext() != null){
                queueTarget.getNext().setPrevious(nodeToAdd);
                nodeToAdd.setNext(queueTarget.getNext());
            }
            queueTarget.setNext(nodeToAdd);
            nodeToAdd.setPrevious(queueTarget);
        } else {
            nonPriorityTasks.enqueue(element.getId(), element);
        }
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
