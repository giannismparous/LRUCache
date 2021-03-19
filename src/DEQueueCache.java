public class DEQueueCache<K, V> implements Cache<K,V>{

    private int maxN;
    private long lookUps;
    private long hits;
    private long misses;
    private int size;
    private Hashmap<K, Node<K, V>> CacheHashmap;
    private Node<K, V> LRU;
    private Node<K, V> MRU;
	
	private class Node<L, M> {
        Node<L, M> prev;
        Node<L, M> next;
        L key;
        M value;
		
		public Node(L key, M value){
			this.key=key;
			this.value=value;
		}
    }

    public DEQueueCache(int maxN){
    	this.maxN = maxN;
    	hits=0;
    	misses=0;
    	lookUps=0;
        this.size = 0;
        LRU = new Node<K, V>(null, null);
        MRU = LRU;
        CacheHashmap = new Hashmap<K, Node<K, V>>(maxN);
    }

    public V lookUp(K key){
    	lookUps++;
        Node<K, V> temp = CacheHashmap.getValue(key);
        
        if (temp == null){
        	misses++;
            return null;
        }
        
        else if (temp.key == MRU.key){
        	hits++;
            return temp.value;
        }
        
        hits++;
        updateMRU(temp);
        
        return temp.value;
    }

    public void store(K key, V value){
        if (CacheHashmap.getValue(key)!=null){//stoixeio me to sygkekrimeno key den vrethike
            return;
        }

        Node<K, V> node = new Node<K, V>(key, value);
        addMRU(node);
        
        if (size == maxN)removeLRU();//exoume hdh maxN stoixeia ara prepei se kathe eisagwgh MRU na kanoyme kai mia exagwgh tou LRU
        else if (size < maxN){
            if (size == 0){//se periptwsh pou den yparxei stoixeio sthn cache
                LRU = node;
            }
            size++;
        }
    }
    
    public long getHits() {
    	return hits;
    	}
    
    public long getMisses() {
    	return misses;
    	}
    
    public long getNumberOfLookUps() {
    	return lookUps;
    	}
    
    public double getHitRatio() {
    	return ((double)hits)/lookUps;
    	}
    
    private void addMRU(Node<K,V> node){//vazei MRU
        node.prev=MRU;
        MRU.next = node;
        CacheHashmap.put(node.key, node);
        MRU = node;
    }
    
    private void removeLRU() {//vgazei to LRU
    	CacheHashmap.remove(LRU.key);
        LRU = LRU.next;
        LRU.prev = null;
    }
    
    private void updateMRU(Node<K,V> temp) {//enhmerwnei th seira twn stoixeiwn
    	Node<K, V> next = temp.next;
        Node<K, V> prev = temp.prev;

        if (temp.key == LRU.key){//periptwsh pou to temp einai to LRU
            next.prev = null;
            LRU = next;
        }

        else if (temp.key != MRU.key){//periptwsh pou to temp vrisketai anamesa sta LRU - MRU
            prev.next = next;
            next.prev = prev;
        }
        
        //enhmerwsh MRU
        temp.prev = MRU;
        MRU.next = temp;
        MRU = temp;
        MRU.next = null;
    }
}