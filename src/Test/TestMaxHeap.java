package Test;

import Exceptions.HeapSizeException;
import model.MaxHeap;
import model.TaskReminder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMaxHeap{

    private MaxHeap maxHeap;

    @Before
    public void setUp(){
        maxHeap = new MaxHeap();
    }

    @Test
    public void testInsertAndExtractMaximum() throws HeapSizeException{
        assertTrue(maxHeap.isEmpty());
        assertEquals(0, maxHeap.getHeapSize());

        TaskReminder task1 = new TaskReminder(null, null, null, null, 30, true);
        maxHeap.insert(task1);
        assertFalse(maxHeap.isEmpty());
        assertEquals(1, maxHeap.getHeapSize());

        TaskReminder task2 = new TaskReminder(null, null, null, null, 83, true);
        maxHeap.insert(task2);
        assertEquals(2, maxHeap.getHeapSize());

        TaskReminder maxTask = maxHeap.extractMaximum();
        assertEquals(83, maxTask.getImportanceLevel());
        assertEquals(1, maxHeap.getHeapSize());

        TaskReminder maxTask2 = maxHeap.maximum();
        assertEquals(30, maxTask2.getImportanceLevel());
    }



    @Test(expected = HeapSizeException.class)
    public void testInsertMaxHeapSizeExceeded() throws HeapSizeException {
        for (int i = 0; i <= 1000; i++) {
            TaskReminder task = new TaskReminder(null, null, null, null, i, true);
            maxHeap. insert(task);
        }
    }

    @Test
    public void testIncreaseKey() throws HeapSizeException {
        TaskReminder task1 = new TaskReminder(null, null, null, null, 30, true);
        maxHeap.insert(task1);

        TaskReminder task2 = new TaskReminder(null, null, null, null, 10, true);
        maxHeap.increaseKey(0, task2);

        TaskReminder maxTask = maxHeap.maximum();
        assertEquals(30, maxTask.getImportanceLevel());
    }



}

