package model;

public class Stack<K, V> implements iStack<K, V> {

    private Node<K, V> top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }

    @Override
    public void push(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    @Override
    public V pop() {
        if (isEmpty()) {
            return null;
        }
        Node<K, V> poppedNode = top;
        top = top.getNext();
        size--;
        return poppedNode.getValue();
    }

    @Override
    public V peek() {
        if (isEmpty()) {
            return null;
        }
        return top.getValue();
    }

    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public int size() {
        return size;
    }
}
