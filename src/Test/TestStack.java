package Test;

import model.Stack;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStack{

    private Stack<String, Integer> stack;

    @Before
    public void setUp(){
        stack = new Stack<>();
    }

    @Test
    public void testPushAndPop(){
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());

        stack.push("key1", 12);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());

        stack.push("key2", 56);
        assertEquals(2, stack.size());

        Integer value2 = stack.pop();
        assertEquals(56, (int) value2);
        assertEquals(1, stack.size());

        Integer value1 = stack.pop();
        assertEquals(12, (int) value1);
        assertTrue(stack.isEmpty());
    }



    @Test
    public void testPeek(){
        assertNull(stack.peek());

        stack.push("key1", 12);
        stack.push("key2", 56);

        Integer peekedValue = stack.peek();
        assertNotNull(peekedValue);
        assertEquals(56, (int) peekedValue);
    }



    @Test
    public void testIsEmpty(){
        assertTrue(stack.isEmpty());

        stack.push("key1", 12);

        assertFalse(stack.isEmpty());
    }


    @Test
    public void testSize(){
        assertEquals(0, stack.size());

        stack.push("key1", 12);
        assertEquals(1, stack.size());

        stack.push("key2", 56);
        assertEquals(2, stack.size());

        stack.pop();
        assertEquals(1, stack.size());
    }

}
