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
        String msg = "New priority task added!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            TaskReminder task = new TaskReminder(id, title, description, dueDate, importance, true);
            taskReminderTable.insert(id, task);
            if (isPriority){
                priorityTasks.insert(task);
            } else {
                nonPriorityTasks.enqueue(id, task);
                msg = "New no-priority task added!";
            }
            actions.push(id, new Action("Add element", task));
        } catch (HeapSizeException | DuplicatedObjectException|  InvalidDateException e){
            msg = e.getMessage();
        }
        return msg;
    }

    public String editReminder(String id, String title, String description, String dueDateInput, boolean isUndo){
        String msg = "Reminder edited successfully!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            Node<String, TaskReminder> hashNode = taskReminderTable.search(id);
            if (hashNode == null || hashNode.getValue().isTask()){
                msg = "Error: The reminder with the entered ID doesn't exist.";
            } else {
                TaskReminder reminder = hashNode.getValue();
                if (!isUndo){
                    TaskReminder original = new TaskReminder(reminder);
                    actions.push(id, new Action("Edit element", original));
                }
                reminder.setTitle(title);
                reminder.setDescription(description);
                reminder.setDueDate(dueDate);
            }
        } catch (InvalidDateException e){
            msg = e.getMessage();
        }
        return msg;
    }

    public String editNoPriorityTask(String id, String title, String description, String dueDateInput, boolean isUndo){
        String msg = "No-priority task edited successfully!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            Node<String, TaskReminder> hashNode = taskReminderTable.search(id);
            if (hashNode == null || !hashNode.getValue().isTask()){
                msg = "Error: There is no task with the entered ID.";
            } else {
                Node<String, TaskReminder> queueNode = nonPriorityTasks.search(id);
                if (queueNode != null) {
                    TaskReminder task = hashNode.getValue();
                    if (!isUndo){
                        TaskReminder original = new TaskReminder(task);
                        actions.push(id, new Action("Edit element", original));
                    }
                    //update the attributes of the element in the table
                    task.setTitle(title);
                    task.setDescription(description);
                    task.setDueDate(dueDate);
                    //update the attributes of the element in the heap
                    queueNode.getValue().setTitle(title);
                    queueNode.getValue().setDescription(description);
                    queueNode.getValue().setDueDate(dueDate);
                } else {
                    msg = "Error: The entered ID does not correspond to a no-priority task.";
                }
            }
        } catch (InvalidDateException e){
            msg = e.getMessage();
        }
        return msg;
    }

    public String editPriorityTask(String id, String title, String description, String dueDateInput, int importance, boolean isUndo){
        String msg = "Priority task edited successfully!";
        try {
            Calendar dueDate = validateDueDate(dueDateInput);
            Node<String, TaskReminder> hashNode = taskReminderTable.search(id);
            if (hashNode == null || !hashNode.getValue().isTask()){
                msg = "Error: There is no task with the entered ID.";
            } else {
                TaskReminder task = hashNode.getValue();
                if (task.getImportanceLevel() == 0){
                    msg = "Error: The entered ID does not correspond to a priority task.";
                } else {
                    if (!isUndo){
                        TaskReminder original = new TaskReminder(task);
                        actions.push(id, new Action("Edit element", original));
                    }
                    //update the attributes of the element
                    task.setTitle(title);
                    task.setDescription(description);
                    task.setDueDate(dueDate);
                    int importanceCopy = task.getImportanceLevel();
                    task.setImportanceLevel(importance);
                    if (importance > importanceCopy){
                        int i = priorityTasks.searchTaskIndex(task);
                        priorityTasks.increaseKey(i,task);
                    } else {
                        priorityTasks.maxHeapify(0);
                    }
                }
            }
        } catch (InvalidDateException e){
            msg = e.getMessage();
        }
        return msg;
    }

    public String deleteElement(String id, boolean isUndo){
        String msg = "Reminder removed successfully!";
        Node<String, TaskReminder> hashNode = taskReminderTable.search(id);
        if (hashNode != null){
            TaskReminder element = hashNode.getValue();
            //save the previous reference of the node to delete in the hash table
            Node<String, TaskReminder> targetHashNode = hashNode.getPrevious();
            String targetHashID = null;
            if (targetHashNode != null){
                targetHashID = targetHashNode.getValue().getId();
            }
            //delete the node from the table
            taskReminderTable.delete(id);
            if (element.isTask()){
                if (element.getImportanceLevel() != 0){
                    msg = deletePriorityTask(element);
                    if(!isUndo){
                        actions.push(id, new Action("Delete element", element, targetHashID));
                    }
                } else {
                    String targetQueueID = deleteNoPriorityTask(element);
                    if (!isUndo){
                        actions.push(id, new Action("Delete element", element, targetHashID, targetQueueID));
                    }
                    msg = "No-priority task deleted successfully!";
                }
            } else {
                if (!isUndo){
                    actions.push(id, new Action("Delete element", element, targetHashID));
                }
            }
        } else {
            msg = "Error: no element with the entered ID was found.";
        }
        return msg;
    }

    private String deletePriorityTask(TaskReminder priorityTask){
        int index = priorityTasks.searchTaskIndex(priorityTask);
        priorityTasks.getHeap()[index] = priorityTasks.getHeap()[priorityTasks.getHeapSize() - 1];
        priorityTasks.getHeap()[priorityTasks.getHeapSize() - 1] = null;
        priorityTasks.setHeapSize(priorityTasks.getHeapSize()-1);
        priorityTasks.maxHeapify(0);
        return "Priority task removed successfully!";
    }

    private String deleteNoPriorityTask(TaskReminder noPriorityTask){
        Node<String, TaskReminder> queueNode = nonPriorityTasks.search(noPriorityTask.getId());
        Node<String, TaskReminder> targetQueueNode = queueNode.getPrevious();
        String targetQueueID = null;
        if (targetQueueNode != null){
            targetQueueID = targetQueueNode.getValue().getId();
        }
        nonPriorityTasks.delete(queueNode);
        return targetQueueID;
    }

    public void undoAction(){
        if (!actions.isEmpty()) {
            Action action = actions.pop();
            String actionType = action.getType();
            TaskReminder record = action.getRecord();
            String id = record.getId();
            if (actionType.equals("Add element")) {
                deleteElement(id, true);
            } else {
                int importance = record.getImportanceLevel();
                if (actionType.equals("Edit element")) {
                    String title = record.getTitle();
                    String description = record.getDescription();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dueDate = dateFormat.format(record.getDueDate().getTime());
                    if (!record.isTask()){
                        editReminder(id, title, description, dueDate, true);
                    } else {
                        if (importance != 0){
                            editPriorityTask(id, title, description, dueDate, importance, true);
                        } else {
                            editNoPriorityTask(id, title, description, dueDate, true);
                        }
                    }
                } else {
                    String targetHashID = action.getTargetHashID();
                    returnElementToHash(targetHashID, record);
                    if (importance != 0){
                        try {
                            priorityTasks.insert(record);
                        } catch (HeapSizeException ignored) {}
                    } else if (record.isTask()){
                        String targetQueueID = action.getTargetQueueID();
                        returnElementToQueue(targetQueueID, record);
                    }
                }
            }
        }
    }

    private void returnElementToHash(String targetHashID, TaskReminder element){
        if (targetHashID != null){
            Node<String, TaskReminder> nodeToAdd = new Node<>(element.getId(), element);
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

    }

    private void returnElementToQueue(String targetQueueID, TaskReminder element){
        Node<String, TaskReminder> nodeToAdd = new Node<>(element.getId(), element);
        Node<String, TaskReminder> queueTarget = nonPriorityTasks.search(targetQueueID);
        if (queueTarget != null){
            if (queueTarget.getNext() != null){
                queueTarget.getNext().setPrevious(nodeToAdd);
                nodeToAdd.setNext(queueTarget.getNext());
            }
            queueTarget.setNext(nodeToAdd);
            nodeToAdd.setPrevious(queueTarget);
        } else {
            if (nonPriorityTasks.isEmpty()){
                nonPriorityTasks.enqueue(element.getId(), element);
            } else {
                nodeToAdd.setNext(nonPriorityTasks.getHead());
                nonPriorityTasks.getHead().setPrevious(nodeToAdd);
                nonPriorityTasks.setHead(nodeToAdd);
            }
        }
        nonPriorityTasks.setSize(nonPriorityTasks.size() + 1);
    }

    public String showList(){
        if (!taskReminderTable.isEmpty()){
            StringBuilder list = new StringBuilder();
            if (!priorityTasks.isEmpty()){
                list.append("PRIORITY TASKS (Sorted by importance):\n");
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
            }
            if (!nonPriorityTasks.isEmpty()){
                list.append("\nNON PRIORITY TASKS (Sorted by order of arrival):\n");
                Node<String, TaskReminder> currentQueueNode = nonPriorityTasks.peek();
                while (currentQueueNode != null) {
                    list.append("\n").append(currentQueueNode.getValue()).append("\n");
                    currentQueueNode = currentQueueNode.getNext();
                }
            }
            boolean oneReminder = false;
            for (int i = 0; i < taskReminderTable.getTable().length; i++){
                Node<String, TaskReminder> currentHashNode = taskReminderTable.getTable()[i];
                while (currentHashNode != null) {
                    if (!currentHashNode.getValue().isTask()) {
                        if (!oneReminder){
                            list.append("\nREMINDERS:\n");
                        }
                        list.append("\n").append(currentHashNode.getValue()).append("\n");
                        oneReminder = true;
                    }
                    currentHashNode = currentHashNode.getNext();
                }
            }
            return list.toString();
        }
        return "Error: there are no elements registered yet.";
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