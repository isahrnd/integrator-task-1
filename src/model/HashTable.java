package model;
import Exceptions.DuplicatedObjectException;

@SuppressWarnings("unchecked")
public class HashTable<K, V> implements iHashTable<K, V>{

    private final int TABLE_SIZE = 10;
    private final Node<K, V>[] table;

    public HashTable() {
        table = new Node[TABLE_SIZE];
    }

    @Override
    public void insert(K key, V value) throws DuplicatedObjectException{
        int index = hash(key);
        Node<K, V> newNode = new Node<>(key, value);
        if (table[index] != null) {
            Node<K, V> currentNode = table[index];
            while (currentNode != null) {
                if (currentNode.getKey().equals(key)){
                    throw new DuplicatedObjectException("Duplicate object detected.");
                }
                currentNode = currentNode.getNext();
            }
            newNode.setNext(table[index]);
            table[index].setPrevious(newNode);
        }
        table[index] = newNode;
    }

    @Override
    public Node<K, V> search(K key) {
        int index = hash(key);
        if (table[index] != null){
            Node<K, V> currentNode = table[index];
            while (currentNode != null) {
                if (currentNode.getKey().equals(key)) {
                    return currentNode;
                }
                currentNode = currentNode.getNext();
            }
        }
        return null;
    }

    @Override
    public void delete(K key) {
        int index = hash(key);
        Node<K, V> currentNode = table[index];
        Node<K, V> previousNode = null;
        while (currentNode != null) {
            if (currentNode.getKey().equals(key)) {
                if (previousNode != null) {
                    previousNode.setNext(currentNode.getNext());
                } else {
                    table[index] = currentNode.getNext();
                }
                currentNode.setNext(null);
                return;
            }
            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }
    }

    @Override
    public int hash(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % TABLE_SIZE;
    }

    public boolean isEmpty() {
        for (Node<K, V> kvNode : table) {
            if (kvNode != null) {
                return false;
            }
        }
        return true;
    }

    public Node<K, V>[] getTable() {
        return table;
    }
}
