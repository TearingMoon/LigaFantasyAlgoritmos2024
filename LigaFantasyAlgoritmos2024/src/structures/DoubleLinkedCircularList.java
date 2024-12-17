package structures;

import java.util.Iterator;

public class DoubleLinkedCircularList<T> implements Iterable<T> 
{
	private DoubleNode<T> head;
	private int listCount;
	
	public DoubleLinkedCircularList(T headValue)
	{
		head.SetValue(headValue);
		head.SetNext(head);
		head.SetPrevious(head);
		listCount = 1;
	}
	
	public DoubleLinkedCircularList()
	{
		head = null;
		listCount = 0;
	}
	
	public void InsertHead(T value)
	{
		if(listCount != 0)
		{
			DoubleNode<T> newHead = new DoubleNode<T>(value);
			
			newHead.SetNext(head);
			newHead.SetPrevious(head.GetPrevious());
			head.GetPrevious().SetNext(newHead);
			head.SetPrevious(newHead);
			
			head = newHead;
		}
		else
		{
			head = new DoubleNode<T>(value);
			head.SetPrevious(head);
			head.SetNext(head);
		}
		
		listCount++;
	}
	
	public void Insert(T value)
	{
		if(listCount != 0)
		{
			DoubleNode<T> newLast = new DoubleNode<T>(value);
			DoubleNode<T> last = head.GetPrevious();
			
			last.SetNext(newLast);
			newLast.SetPrevious(last);
			newLast.SetNext(head);
			head.SetPrevious(newLast);
		}
		else
		{
			head = new DoubleNode<T>(value);
			head.SetPrevious(head);
			head.SetNext(head);
		}
		
		listCount++;
	}
	
	public void Insert(T value, int index)
	{
		if(index > listCount || index < 0)
		{
			return;
		}
		else if(index == listCount)
		{
			Insert(value);
		}
		else if(index == 0)
		{
			InsertHead(value);
		}
		else if(index <= listCount/2)
		{
			DoubleNode<T> newNode = new DoubleNode<T>(value);
			DoubleNode<T> aux = head;
			
			for(int i = 0; i<listCount; i++)
			{
				if(i != index-1)
				{
					aux = aux.GetNext();
				}
				else
				{
					newNode.SetNext(aux.GetNext());
					aux.SetNext(newNode);
					newNode.SetPrevious(aux);
					newNode.GetNext().SetNext(newNode);
				}
			}
		}
		else
		{
			DoubleNode<T> newNode = new DoubleNode<T>(value);
			DoubleNode<T> aux = head.GetPrevious();
			
			for(int i = listCount-1; i>=0; i--)
			{
				if(i != index-1)
				{
					aux = aux.GetPrevious();
				}
				else
				{
					newNode.SetNext(aux.GetNext());
					aux.SetNext(newNode);
					newNode.SetPrevious(aux);
					newNode.GetNext().SetNext(newNode);
				}
			}
		}
		
		listCount++;
	}
	
	public void Clear()
	{
		head = null;
		listCount = 0;
	}
	
	public int GetSize()
	{
		return listCount;
	}
	
	public boolean IsEmpty()
	{
		return listCount == 0;
	}
	
	public T Get(int index)
	{
		if(index > listCount || index < 0)
		{
			return null;
		}
		else if(index == listCount)
		{
			return head.GetPrevious().GetValue();
		}
		else if(index == 0)
		{
			return head.GetValue();
		}
		if(index <= listCount/2)
		{
			DoubleNode<T> aux = head;
			
			for(int i = 0; i<listCount; i++)
			{
				if(i != index)
				{
					aux = aux.GetNext();
				}
				else
				{
					return aux.GetValue();
				}
			}
		}
		else
		{
			DoubleNode<T> aux = head.GetPrevious();
			
			for(int i = listCount-1; i>=0; i--)
			{
				if(i != index)
				{
					aux = aux.GetPrevious();
				}
				else
				{
					return aux.GetValue();
				}
			}
		}
		
		return null;
	}
	
	public T Remove(int index)
	{
		if(index >= listCount || index < 0)
		{
			return null;
		}
		else if(index == listCount-1)
		{
			T returnValue = head.GetPrevious().GetValue();
			head.GetPrevious().GetPrevious().SetNext(head);
			head.SetPrevious(head.GetPrevious().GetPrevious());
			listCount--;
			return returnValue;
		}
		else if(index == 0)
		{
			T returnValue = head.GetValue();
			head.GetNext().SetPrevious(head.GetPrevious());
			head.GetPrevious().SetNext(head.GetNext());
			head = head.GetNext();
			listCount--;
			return returnValue;
		}
		if(index <= listCount/2)
		{
			DoubleNode<T> aux = head;
			
			for(int i = 0; i<listCount; i++)
			{
				if(i != index)
				{
					aux = aux.GetNext();
				}
				else
				{
					T returnValue = aux.GetValue();
					aux.GetNext().SetPrevious(aux.GetPrevious());
					aux.GetPrevious().SetNext(aux.GetNext());
					listCount--;
					return returnValue;
				}
			}
		}
		else
		{
			DoubleNode<T> aux = head.GetPrevious();
			
			for(int i = listCount-1; i>=0; i--)
			{
				if(i != index)
				{
					aux = aux.GetPrevious();
				}
				else
				{
					T returnValue = aux.GetValue();
					aux.GetNext().SetPrevious(aux.GetPrevious());
					aux.GetPrevious().SetNext(aux.GetNext());
					listCount--;
					return returnValue;
				}
			}
		}
		
		return null;
	}
	
	public boolean Remove(T value)
	{
		DoubleNode<T> aux = head;
		
		for(int i = 0; i<listCount; i++)
		{
			if(aux.GetValue() != value)
			{
				aux = aux.GetNext();
			}
			else
			{
				if(aux != head)
				{
					aux.GetNext().SetPrevious(aux.GetPrevious());
					aux.GetPrevious().SetNext(aux.GetNext());
					listCount--;
					return true;
				}
				else
				{
					head.GetNext().SetPrevious(head.GetPrevious());
					head.GetPrevious().SetNext(head.GetNext());
					head = head.GetNext();
					listCount--;
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean Contains(T value)
	{		
		for(T item : this)
		{
			if(item == value)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int IndexOf(T value)
	{
		int i = 0;
		
		for(T item : this)
		{
			if(item == value)
			{
				return i;
			}
			else i++;
		}
		
		return -1;
	}
	
	public Iterator<T> iterator()
	{
	  return new ListIterator();
	}
	
	class ListIterator implements Iterator<T>
	{
		private int index = 0;
		
		public boolean hasNext()
		{
			return index < listCount;
		}
		
		public T next()
		{
			return Get(index++);
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}
