package model;

import Exceptions.DuplicatedObjectException;

public interface iHashTable <K,V> {
    void insert(K key, V value) throws DuplicatedObjectException;
    V search(K key);
    boolean delete(K key);
    int hash(K key);
}
