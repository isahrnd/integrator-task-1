package model;

import Exceptions.HeapSizeException;

public class MaxHeap implements iMaxPriorityQueue {

    private int heapSize;
    private TaskReminder[] heap;

    public MaxHeap(){
        heap = new TaskReminder[1000];
        heapSize = 0;
    }

    public void maxHeapify(int i){
        int l = left(i);
        int r = right(i);
        int largest;
        if (l < heapSize && heap[l].getImportanceLevel() > heap[i].getImportanceLevel()){
            largest = l;
        } else {
            largest = i;
        }
        if (r < heapSize && heap[r].getImportanceLevel() > heap[largest].getImportanceLevel()){
            largest = r;
        }
        if (largest != i){
            swap(i, largest);
            maxHeapify(largest);
        }
    }

    public boolean isEmpty() {
        return heap[0] == null;
    }

    public int searchTaskIndex(TaskReminder task){
        for (int i = 0; i < heapSize; i++){
            if (heap[i] == task){
                return i;
            }
        }
        return -1;
    }

    public TaskReminder[] getHeap(){
        return heap;
    }

    public int getHeapSize(){
        return heapSize;
    }

    public void setHeapSize(int heapSize){
        this.heapSize = heapSize;
    }

    @Override
    public void insert(TaskReminder task) throws HeapSizeException {
        if(heapSize > 1000){
            throw new HeapSizeException("Error: Maximum size reached.");
        }
        heap[heapSize] = new TaskReminder(null,null, null, null, -1, true);
        increaseKey(heapSize, task);
        heapSize++;
    }

    @Override
    public void increaseKey(int i, TaskReminder key) {
        if (key.getImportanceLevel() < heap[i].getImportanceLevel()) {
            return;
        }
        heap[i] = key;
        while (i > 0 && heap[i].getImportanceLevel() > heap[parent(i)].getImportanceLevel()) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    @Override
    public TaskReminder extractMaximum() {
        if (heapSize < 1){
            return null;
        }
        TaskReminder max = maximum();
        heap[0] = heap[heapSize - 1];
        heapSize--;
        maxHeapify(0);
        return max;
    }

    @Override
    public TaskReminder maximum() {
        return heap[0];
    }

    private int parent(int index) {
        return index / 2;
    }

    private void swap(int i, int j) {
        TaskReminder temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int left(int i){
        return 2 * i + 1;
    }

    private int right(int i){
        return 2 * i + 2;
    }
}
