package structures;

public class LinkedQueue<T>
{
	private DoubleNode<T> front;
	private DoubleNode<T> end;
	private int size;
	
	public LinkedQueue()
	{
		front = end = null;
		size = 0;
	}
	
	public LinkedQueue(T value)
	{
		front = end = new DoubleNode<T>(value);
		size = 1;
	}
	
	@SuppressWarnings("unchecked")
	public void Insert(T... values)
	{
		for(T value : values)
		{
			DoubleNode<T> newNode = new DoubleNode<T>(value);
			if(IsEmpty())
			{
				newNode.SetNext(null);
				newNode.SetPrevious(null);
				front = newNode;
				end = newNode;
			}
			else
			{
				newNode.SetNext(end);
				newNode.SetPrevious(null);
				end.SetPrevious(newNode);
				end = newNode;
			}
			size++;
		}
	}
	
	public T Remove()
	{
		if(!IsEmpty())
		{
			T returnValue = front.GetValue();
			front = front.GetPrevious();
			if(front != null) front.SetNext(null);
			else front = end = null;
			size--;
			return returnValue;
		}
		return null;
	}
	
	public boolean IsEmpty()
	{
		return size == 0;
	}
	
	public void Clear()
	{
		front = end = null;
		size = 0;
	}
	
	public T Front()
	{
		return front.GetValue();
	}
	
	public int Size()
	{
		return size;
	}
}
