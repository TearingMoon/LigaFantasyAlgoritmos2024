package structures;

public class Node<T>
{
	private Node<T> next;
	private T value;
	
	public Node(T value)
	{
		this.value = value;
	}
	
	public T GetValue() { return value; }
	public void SetValue(T value) { this.value = value; }
	
	public Node<T> GetNext() { return next; }
	public void SetNext(Node<T> next) { this.next = next; }
}
