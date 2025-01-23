package structures;

public class PriorityQueue<T>
{
	private LinkedQueue<T>[] queues;
	private int numberOfTiers;
	private int size;
	
	@SuppressWarnings("unchecked")
	public PriorityQueue(int numberOfTiers)
	{
		this.queues = new LinkedQueue[numberOfTiers];
		this.numberOfTiers = numberOfTiers;
		this.size = 0;
		
		for(int i = 0; i<numberOfTiers; i++)
		{
			queues[i] = new LinkedQueue<T>();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Boolean Insert(int priority, T... values)
	{
		if(priority >= 0 && priority < numberOfTiers)
		{
			queues[priority].Insert(values);
			size++;
			return true;
		}
		else return false;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean Insert(T... values)
	{
		queues[0].Insert(values);
		size++;
		return true;
	}
	
	public T Remove(int priority)
	{
		if(priority >= 0 && priority < numberOfTiers)
		{
			T returnValue = queues[priority].Remove();
			size--;
			return returnValue;
		}
		else return null;
	}
	
	public T Remove()
	{
		for(LinkedQueue<T> queue : queues)
		{
			if(!queue.IsEmpty())
			{
				T returnValue = queue.Remove();
				size--;
				return returnValue;
			}
		}
		return null;
	}
	
	public boolean IsEmpty()
	{
		for(LinkedQueue<T> queue : queues)
		{
			if(!queue.IsEmpty()) return false;
		}
		return true;
	}
	
	public boolean IsEmpty(int priority)
	{
		if(priority >= 0 && priority < numberOfTiers)
		{
			if(queues[priority].IsEmpty()) return true;
		}
		return false;
	}
	
	public void Clear()
	{
		for(LinkedQueue<T> queue : queues)
		{
			queue.Clear();
		}
	}
	
	public Boolean Clear(int priority)
	{
		if(priority >= 0 && priority < numberOfTiers)
		{
			queues[priority].Clear();
			return true;
		}
		else return false;
	}
	
	public T Front(int priority)
	{
		if(priority >= 0 && priority < numberOfTiers)
		{
			return queues[priority].Front();
		}
		else return null;
	}
	
	public int Size()
	{
		return size;
	}
	
	public int Size(int priority)
	{
		if(priority >= 0 && priority < numberOfTiers)
		{
			return queues[priority].Size();
		}
		else return -1;
	}
}
