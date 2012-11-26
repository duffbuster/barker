package barker;


/**
 * Generic Queue structure for Barker, a project at the 
 *      University of Mary Washington.
 * @author David Justis
 */
public class BarkerQueue<T>{
    
    Node<T> first;
    Node<T> last;
    int size;
    
    /**
     * Class for Node in Generic structure BarkerQueue
     * @param <T> 
     */
    protected class Node<T>{
        T data;
        Node<T> next;
        
        Node(){
            this.data = null;
            this.next = null;
        }
        
        Node(T input){
            this.data=input;
            this.next=null;
        }
    }
    
    /**
     * Constructor for empty Queue.
     */
    public BarkerQueue(){
        
        first=null;
        last=null;
    }
    
    /**
     * Method to add element to front of Queue.
     * @param input 
     */
    public void insert(T input){
        Node<T> newNode = new Node(input);
        
        if(size==0){
            first=newNode;
            first.next=last;
            last=first;
        }
        
        else{
            last.next=newNode;
            last=newNode;
        }
        
        size++;
    }
    
    /**
     * Returns the element at the front of the Queue.
     * @return 
     */
    public T peek(){
        return first.data;
    }
    
    /**
     * Returns and removes the element at the front of the Queue.
     * @return 
     */
    public T pop(){
        
        T hold = first.data;
        Node<T> newFirst=first.next;
        first=newFirst;
        size--;
        
        return hold;
    }
    
}
