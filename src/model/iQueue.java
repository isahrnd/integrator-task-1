package model;

public interface iQueue<K, V> {
    void enqueue(K key, V value);
    V dequeue();
    boolean isEmpty();
    int size();
}
