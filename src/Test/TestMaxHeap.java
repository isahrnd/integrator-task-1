package Test;

import Exceptions.HeapSizeException;
import model.MaxHeap;
import model.TaskReminder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMaxHeap {

    private MaxHeap maxHeap;

    @Before
    public void setUp() {
        maxHeap = new MaxHeap();
    }

    @Test
    public void testInsertAndExtractMaximum() throws HeapSizeException {
        assertTrue(maxHeap.isEmpty());
        assertEquals(0, maxHeap.getHeapSize());

        TaskReminder task1 = new TaskReminder(null, null, null, null, 42, true);
        maxHeap.insert(task1);
        assertFalse(maxHeap.isEmpty());
        assertEquals(1, maxHeap.getHeapSize());

        TaskReminder task2 = new TaskReminder(null, null, null, null, 100, true);
        maxHeap.insert(task2);
        assertEquals(2, maxHeap.getHeapSize());

        TaskReminder maxTask = maxHeap.extractMaximum();
        assertEquals(83, maxTask.getImportanceLevel());
        assertEquals(1, maxHeap.getHeapSize());

        TaskReminder maxTask2 = maxHeap.maximum();
        assertEquals(30, maxTask2.getImportanceLevel());
    }
}

