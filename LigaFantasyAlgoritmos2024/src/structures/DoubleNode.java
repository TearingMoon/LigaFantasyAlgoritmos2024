package structures;

public class DoubleNode<T>
{
	private DoubleNode<T> next, previous;
	private T value;
	
	public DoubleNode(T value)
	{
		this.value = value;
	}
	
	public T GetValue() { return value; }
	public void SetValue(T value) { this.value = value; }
	
	public DoubleNode<T> GetNext() { return next; }
	public void SetNext(DoubleNode<T> next) { this.next = next; }
	
	public DoubleNode<T> GetPrevious() { return previous; }
	public void SetPrevious(DoubleNode<T> previous) { this.previous = previous; }
}
