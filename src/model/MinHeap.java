package model;

import java.util.ArrayList;

public class MinHeap implements MinPriorityQueue {

    private ArrayList<Task> tasks;
    private int size;

    public MinHeap(){
        tasks = new ArrayList<>();
    }

    public void buildMinHeap(){

    }

    public void minHeapify(){

    }

    public void heapsort(){
        //no hace nada para esta integradora.
    }

    @Override
    public void insert(Task task) {

    }

    @Override
    public Task extractMin() {
        return null;
    }

    @Override
    public Task getMin() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
