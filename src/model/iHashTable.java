package model;

import Exceptions.DuplicatedObjectException;

public interface iHashTable <K,V> {
    void insert(K key, V value) throws DuplicatedObjectException;
    Node<K,V> search(K key);
    void delete(K key);
    int hash(K key);
    boolean isEmpty();
}
