package model;

public interface MinPriorityQueue {
    void insert(TaskReminder task);
    TaskReminder extractMin();
    TaskReminder getMin();
    boolean isEmpty();
}
