package model;

public class Queue<K, V> implements iQueue<K, V>{

    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;

    public Queue(){
        size = 0;
    }

    public Node<K, V> search(K key){
        Node<K, V> currentNode = head;
        while (currentNode != null) {
            if (currentNode.getKey().equals(key)){
                return currentNode;
            }
            currentNode = currentNode.getNext();
        }
        return null;
    }

    public void delete(Node<K, V> nodeToDelete){
        //node to delete is head.
        if (nodeToDelete.equals(head)) {
            head = nodeToDelete.getNext();
            if (head == null) {
                tail = null;
            } else {
                head.setPrevious(null);
            }
            return;
        }

        //node to delete is tail
        if (nodeToDelete.equals(tail)){
            tail = nodeToDelete.getPrevious();
            tail.setNext(null);
            return;
        }

        Node<K, V> currentNode = head;
        while (currentNode != null) {
            if (currentNode.equals(nodeToDelete)){
                Node<K, V> prevNode = currentNode.getPrevious();
                Node<K, V> nextNode = currentNode.getNext();
                prevNode.setNext(nextNode);
                nextNode.setPrevious(prevNode);
                return;
            }
            currentNode = currentNode.getNext();
        }
    }

    @Override
    public void enqueue(K key, V value){
        Node<K, V> node = new Node<>(key, value);
        if (isEmpty()) {
            head = node;
        } else {
            node.setPrevious(tail);
            tail.setNext(node);
        }
        tail = node;
        size++;
    }

    @Override
    public V dequeue(){
        if (isEmpty()){
            return null;
        }
        V value = head.getValue();
        Node<K, V> newHead = head.getNext();
        if (newHead != null){
            newHead.setPrevious(null);
        } else{
            tail = null;
        }
        head = newHead;
        size--;
        return value;
    }

    public Node<K, V> peek(){
        return head;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public int size(){
        return size;
    }

}
