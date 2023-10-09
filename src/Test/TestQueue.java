package Test;

import model.Node;
import model.Queue;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestQueue{

    private Queue<String, Integer> queue;

    @Before
    public void setUp(){
        queue = new Queue<>();
    }


    @Test
    public void testEnqueueAndDequeue(){
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());

        queue.enqueue("key1", 18);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());

        queue.enqueue("key2", 97);
        assertEquals(2, queue.size());

        Integer value1 = queue.dequeue();
        assertEquals(18, (int) value1);
        assertEquals(1, queue.size());

        Integer value2 = queue.dequeue();
        assertEquals(97, (int) value2);
        assertTrue(queue.isEmpty());
    }



    @Test
    public void testPeek(){
        assertNull(queue.peek());

        queue.enqueue("key1", 18);
        queue.enqueue("key2", 97);

        Node<String, Integer> peekedNode = queue.peek();
        assertNotNull(peekedNode);
        assertEquals(18, (int) peekedNode.getValue());
    }



    @Test
    public void testDelete(){
        queue.enqueue("key1", 18);
        queue.enqueue("key2", 97);

        Node<String, Integer> nodeToDelete = queue.peek();
        assertNotNull(nodeToDelete);

        queue.delete(nodeToDelete);

        Node<String, Integer> deletedNode = queue.peek();
        assertNotNull(deletedNode);
        assertEquals(97, (int) deletedNode.getValue());
    }



    @Test
    public void testIsEmpty(){
        assertTrue(queue.isEmpty());

        queue.enqueue("key1", 18);

        assertFalse(queue.isEmpty());
    }



    @Test
    public void testSize(){
        assertEquals(0, queue.size());

        queue.enqueue("key1", 18);
        assertEquals(1, queue.size());

        queue.enqueue("key2", 97);
        assertEquals(2, queue.size());

        queue.dequeue();
        assertEquals(1, queue.size());
    }
}