package Test;

import Exceptions.DuplicatedObjectException;

import model.HashTable;
import model.Node;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestHashTable{

    private HashTable<String, Integer> hashTable;

    @Before
    public void setUp(){
        hashTable = new HashTable<>();
    }


    @Test
    public void testInsertAndSearch() throws DuplicatedObjectException{
        hashTable.insert("key1", 27);
        hashTable.insert("key2", 53);

        Node<String, Integer> node1 = hashTable.search("key1");
        Node<String, Integer> node2 = hashTable.search("key2");
        Node<String, Integer> node3 = hashTable.search("noneKey");

        assertNotNull(node1);
        assertEquals(27, (int) node1.getValue());

        assertNotNull(node2);
        assertEquals(53, (int) node2.getValue());

        assertNull(node3);
    }



    @Test(expected = DuplicatedObjectException.class)
    public void testInsertDuplicateKey() throws DuplicatedObjectException{
        hashTable.insert("key1", 27);
        hashTable.insert("key1", 53);
    }



    @Test
    public void testDelete() throws DuplicatedObjectException{
        hashTable.insert("key1", 27);
        hashTable.insert("key2", 53);

        hashTable.delete("key1");

        Node<String, Integer> node1 = hashTable.search("key1");
        Node<String, Integer> node2 = hashTable.search("key2");

        assertNull(node1);
        assertNotNull(node2);
        assertEquals(53, (int) node2.getValue());
    }



    @Test
    public void testIsEmpty() throws DuplicatedObjectException{
        assertTrue(hashTable.isEmpty());

        hashTable.insert("key1", 27);

        assertFalse(hashTable.isEmpty());
    }
}
