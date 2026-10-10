import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFU {

    int capacity;
    int minFreq;
    // key -> value
    Map<Integer,Integer> cache;
    // key -> freq
    Map<Integer,Integer> freq;
    // freq -> key
    Map<Integer, LinkedHashSet<Integer>> freq2Keys;

    LFU(int capacity){
        this.capacity = capacity;
        this.minFreq = 0;
        cache = new HashMap<>();
        freq = new HashMap<>();
        freq2Keys = new HashMap<>();
    }

    public int get(int key){
        if(!cache.containsKey(key)){
            return -1;
        }

        increaseFreq(key);
        return cache.get(key);
    }

    public void put(int key,int value){
        if (cache.containsKey(key)) {
            cache.put(key, value);
            increaseFreq(key);
            return;
        }
        if(cache.size()>capacity){
            removeMinFreq();
        }
        cache.put(key,value);
        freq.put(key,1);
        freq2Keys.computeIfAbsent(1,k->new LinkedHashSet<>()).add(key);
        minFreq = 1;
    }

    public void increaseFreq(int key){
        int oldFreq = freq.get(key);
        LinkedHashSet<Integer> keys = freq2Keys.get(oldFreq);
        keys.remove(key);
        if(keys.isEmpty()){
            freq2Keys.remove(oldFreq);
            if(minFreq==oldFreq){
                //即使之前不存在频率为 3 的 key，执行 increaseFreq(A) 后，也一定会存在。
                minFreq++;
            }
        }
        int newFreq = oldFreq + 1;
        freq.put(key,newFreq);
        freq2Keys.computeIfAbsent(newFreq,k->new LinkedHashSet<>()).add(key);
    }

    public void removeMinFreq(){
        LinkedHashSet<Integer> keys = freq2Keys.get(minFreq);
        int deleteKey = keys.iterator().next();
        keys.remove(deleteKey);
        if(keys.isEmpty()){
            freq2Keys.remove(minFreq);
        }
        cache.remove(deleteKey);
        freq.remove(deleteKey);
    }
}
