package model;

public interface MinPriorityQueue {
    public void insert(Task task);
    public Task extractMin();
    public Task getMin();
    public boolean isEmpty();
}
