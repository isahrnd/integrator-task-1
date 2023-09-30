package model;

import Exceptions.HeapSizeException;

public interface iMaxPriorityQueue {
    void insert(TaskReminder task) throws HeapSizeException;
    void increaseKey(int i, TaskReminder key);
    TaskReminder extractMaximum();
    TaskReminder maximum();
}
