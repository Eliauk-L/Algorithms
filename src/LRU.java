import java.util.HashMap;
import java.util.Map;

public class LRU {
    class Node{
        int key;
        int val;
        Node prev;
        Node next;
        Node(){}
        Node(int key,int val){
            this.key = key;
            this.val = val;
        }
    }

    Map<Integer,Node> cache;
    int size;
    int capacity;
    Node head;
    Node tail;

    LRU(int capacity){
        this.capacity = capacity;
        size = 0;
        cache = new HashMap<>();
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.next = head;
    }

    public int get(int key){
        Node node = cache.get(key);
        if(node == null){
            return -1;
        }else{
            moveToHead(node);
            return node.val;
        }
    }

    public void put(int key,int val){
        Node node = cache.get(key);
        if(node != null){
            node.val = val;
            moveToHead(node);
        }else{
            Node newnode = new Node(key,val);
            cache.put(key,newnode);
            addToHead(newnode);
            size++;
            if(size>capacity){
                node = removeLast();
                cache.remove(node.key);
                size--;
            }
        }
    }
    public Node removeLast(){
        Node node = tail.prev;
        removeNode(node);
        return node;
    }
    public void moveToHead(Node node){
        removeNode(node);
        addToHead(node);
    }

    public void removeNode(Node node){
        // prev node next
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void addToHead(Node node){
        // head node next
        node.next = head.next;
        node.prev = head;
        head.next.prev  = node;
        head.next = node;
    }
}
