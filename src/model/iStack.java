package model;

public interface iStack<K, V> {
    void push(K key, V value);
    V pop();
    V peek();
    boolean isEmpty();
    int size();
}
