package model;

import Exceptions.DuplicatedObjectException;

public interface iHashTable <K,V> {

    void insert(K key, V value) throws DuplicatedObjectException;
    public V search(K key);
    public boolean delete(K key);
    public int hash(K key);

}
