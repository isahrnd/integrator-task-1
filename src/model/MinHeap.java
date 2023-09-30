package model;

public class MinHeap implements MinPriorityQueue {

    private TaskReminder[] heap;

    public MinHeap(){
        heap = new TaskReminder[1000];
    }

    public void buildMinHeap(){

    }

    public void minHeapify(){

    }

    public void heapsort(){
        //no hace nada para esta integradora.
    }

    @Override
    public void insert(TaskReminder task) {

    }

    @Override
    public TaskReminder extractMin() {
        return null;
    }

    @Override
    public TaskReminder getMin() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
