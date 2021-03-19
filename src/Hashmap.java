
public class Hashmap<K,V> {

	 class Entry<T,U>{//node me pedia k,v,next
		 T k;
		 U v;
     
		 Entry<T, U> next;
     
    	public Entry(T k, U v, Entry<T,U> next) {
	         this.k = k;
	         this.v = v;
	         this.next = next;
	     }
	     public T getKey() {
	         return k;
	     }
	     public U getValue() {
	         return v;
	     	}
	 }
	 
	 private int M;//plhthos keliwn dhladh listwn
	 private Entry<K, V>[] table;
	 
	 Hashmap(int maxN) {
		    M = maxN/5;
		    table = new Entry[M];
		}
	 
	 private int getKey(K k) {//epistrefei to apotelesma tou hashfunction
		    int index = k.hashCode() % M;
		    return index >= 0 ? index : -index;
		}
	 
	 public void put(K k, V v) {//eisagei stoieio
	        int index = getKey(k);
	        Entry<K, V> entry = table[index];
	        table[index] = new Entry<K, V>(k, v, entry);
	    }
	 
	 public V getValue(K k) {//epistrefei to value tou stoixeiou me to antistoixo key
		    int index = getKey(k);
		    Entry<K,V> entry=table[index];
		    while (entry!=null) {
		    	if (k==entry.k || k.equals(entry.k))return entry.getValue();
		    	entry=entry.next;
		    }
		    return null;
		}
	 
	 public V remove(K k) {//exagei stoixeio
		 int index = getKey(k);
		 
		 Entry<K,V> entry=table[index];
		 Entry<K, V> prev = null; 
		 
		 while (entry != null)  { 

	            if (entry.k==k || entry.k.equals(k)) break; 
	            prev = entry; 
	            entry = entry.next;
	            
	        } 
	  
	        if (entry == null)return null; 
	  
	        if (prev != null)prev.next = entry.next; 
	        else table[index]=entry.next; 
	  
	        return entry.getValue(); 
	 }
	 
}
