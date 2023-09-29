package model;

public class Queue<K, V> implements iQueue<K, V>{
    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;

    public Queue() {
        size = 0;
    }

    public void enqueue(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            head = node;
        }
        size++;
    }

    public V dequeue() {
        if (isEmpty()) {
            return null;
        }
        V value = head.getValue();
        head = head.getNext();
        size--;
        if (isEmpty()) {
            tail = null;
        }
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

}
