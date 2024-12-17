package structures;

public class HashTable<T> 
{
	public enum exploration
	{
		LINEAL,
		CUADRATIC
	}
	
	private static class Entry<E>
	{
		private String key;
		private E value;
		private boolean isRegistered;
		
		Entry(String clave, E valor)
		{
			this.key = clave;
			this.value = valor;
			isRegistered = true;
		}

		public String getKey() { return key; }
		@SuppressWarnings("unused")
		public void setKey(String clave) { this.key = clave; }

		public E getValue() { return value; }
		@SuppressWarnings("unused")
		public void setValue(E valor) { this.value = valor; }
		
		public boolean getRegistered() { return isRegistered; }
		public void setRegistered(boolean esAlta) { this.isRegistered = esAlta; }
		
		@Override
		public String toString()
		{
			return " (" + key + " : " + value + " : " + isRegistered + ")";
		}
	}
	
	private Entry<T>[] table;
	private int size;
	private int numOfElements;
	private int numOfRegisteredElements;
	private double loadFactor;
	private exploration explorationMethod;
	
	@SuppressWarnings("unchecked")
	public HashTable(int size, exploration explorationMethod)
	{
		this.size = size;
		this.table = new Entry[size];
		
		for(int i = 0; i<size; i++)
		{
			table[i] = null;
		}
		
		this.explorationMethod = explorationMethod;
		numOfElements = 0;
		loadFactor = 0;
	}
	
	private int hash(String key, int mod, Entry<T>[] table)
	{
		long hashed = transformString(key);
		int index = (int) hashed%mod;
		
		switch(explorationMethod)
		{
		case LINEAL:
			
			while(table[index] != null && !table[index].getKey().equals(key))
			{
				if(++index >= mod) index = 0;
			}
			
			break;
		case CUADRATIC:
			
			int i = 0;
			while(table[index] != null && !table[index].getKey().equals(key))
			{
				i++;
				index += (i^2);
				index %= mod;
			}
			
			break;
		}
		
		return index;
	}
	
	private long transformString(String key) 
	{
		long d=0;
		
		for(int i=0;i<Math.min(10, key.length());i++)
		{
			d=d*29+(int)key.charAt(i);
		}
		
		return Math.abs(d);
	}
	
	public void insert(String key, T value)
	{
		if(numOfElements >= size) return;
		int index = hash(key, size, table);
		table[index] = new Entry<T>(key, value);
		numOfElements++;
		numOfRegisteredElements++;
		loadFactor = (double) numOfElements/size;
		if(loadFactor >= 0.8) System.out.println("\n#### THE LOAD FACTOR IS OVER 80%, consider increasing table size or using table.rehash().");
	}
	
	public T get(String key)
	{
		int index = hash(key, size, table);
		
		if(table[index] != null)
		{
			if(!table[index].getRegistered()) return null;
			table[index].getValue();
		}
		return null;
	}
	
	public void remove(String key)
	{
		int index = hash(key, size, table);
		if(table[index] != null)
		{
			table[index].setRegistered(false);
			numOfRegisteredElements--;
		}
	}
	
	public int size() { return numOfRegisteredElements; }
	
	public boolean isEmpty() { return numOfRegisteredElements == 0; }
	
	public boolean contains(String key)
	{
		int index = hash(key, size, table);
		return (table[index] != null && table[index].getRegistered());
	}
	
	public boolean contains(T value)
	{
		for(Entry<T> e : table)
		{
			if(e != null && e.getValue().equals(value)) return true;
		}
		return false;
	}
	
	public String keyOf(T value)
	{
		for(Entry<T> e : table)
		{
			if(e != null && e.getValue().equals(value)) return e.getKey();
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public void rehash()
	{
		int newSize = nextPrime((int) (numOfElements/0.8));
		Entry<T> newTable[] = new Entry[newSize];
		
		for(int i = 0; i < newSize; i++)
		{
			newTable[i] = null;
		}
		
		for(Entry<T> e : table)
		{
			if(e != null)
			{
				int index = hash(e.key, newSize, newTable);
				newTable[index] = e;
			}
		}
		
		size = newSize;
		table = newTable;
		loadFactor = (double) numOfElements/size;
		System.out.println("\n#### New load factor: " + loadFactor);
	}
	
	private int nextPrime(int n) {
		int R = n;
	    while(!isPrime(++R)){}
	    return R;
	}
	
	private boolean isPrime(int n)
	{
		if (n%2==0) return false;
		
	    for(int i=3;i*i<=n;i+=2)
	    {
	        if(n%i==0) return false;
	    }
	    return true;
	}
	
	@Override
	public String toString()
	{
		String returnValue = "\n";
		
		for(int i = 0; i<size; i++)
		{
			returnValue += "Indice " + i + ":" + table[i] + "\n";
		}
		
		return returnValue;
	}
}
