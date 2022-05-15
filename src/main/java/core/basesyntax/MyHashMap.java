package core.basesyntax;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private int size;
    private int capacity = 16;
    private int threshold = (int) (capacity * 0.75);
    Node<K, V>[] table = new Node[capacity];


    private static class Node<K, T> {
        private K key;
        private T value;
        private Node<K, T> next;

        public Node(K key, T value, Node<K, T> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int calculatePosition (K key) {
        if (key == null) {
            return 0;
        } else if (key.hashCode() % capacity >= 0) {
            return key.hashCode() % capacity;
        } else {
            return -key.hashCode() % capacity;
        }
    }

    private void resize() {
    threshold = (int) (threshold * 2);
    size = 0;
    Node<K, V>[] oldTable = table;
    table = new Node[oldTable.length * 2];
    for (Node<K, V> node : oldTable) {
        while (node != null) {
            put(node.key, node.value);
            node = node.next;
        }
    }
    }

    @Override
    public void put(K key, V value) {
        if (threshold == size) {
            resize();
        }

        int index = calculatePosition(key);
        Node<K, V> newNode = new Node<>(key, value, null);
        if (table[index] == null) {
            table[index] = newNode;
            size++;
            return;
        }
        Node<K, V> currentNode = table[index];
        Node previousNode = null;
        while (currentNode != null) {
            if (key == null) {
                if (key == currentNode.key) {
                    currentNode.value = value;
                    return;
                }
            }
            if (key != null) {
                if (key.equals(currentNode.key)) {
                    currentNode.value = value;
                    return;
                }
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        previousNode.next = newNode;
        size++;
    }

    @Override
    public V getValue(K key) {
        int index = calculatePosition(key);
        Node<K, V> currentNode = table[index];
        while (currentNode != null) {
            if (key == null) {
                if (key == currentNode.key) {
                    return currentNode.value;
                }
            }
            if (key != null) {
                if (key.equals(currentNode.key)) {
                    return currentNode.value;
                }
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }
}
