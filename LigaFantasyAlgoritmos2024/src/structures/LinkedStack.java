package structures;

public class LinkedStack<T>
{
	private Node<T> top;
	private int size;
	
	public LinkedStack()
	{
		top = null;
		size = 0;
	}
	
	public LinkedStack(T value)
	{
		top = new Node<T>(value);
		top.SetNext(null);
		size = 1;
	}
	
	@SuppressWarnings("unchecked")
	public void Push(T... values)
	{
		for(T value : values)
		{
			Node<T> newTop = new Node<T>(value);
			newTop.SetNext(top);
			top = newTop;
			size++;
		}
	}
	
	public T Pop()
	{
		T returnValue = top.GetValue();
		top = top.GetNext();
		size--;
		return returnValue;
	}
	
	public boolean IsEmpty()
	{
		return size == 0;
	}
	
	public void Clear()
	{
		top = null;
		size = 0;
	}
	
	public T Peek()
	{
		return top.GetValue();
	}
	
	public int Size()
	{
		return size;
	}
}
