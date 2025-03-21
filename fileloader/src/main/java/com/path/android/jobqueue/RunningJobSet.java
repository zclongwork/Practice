package com.path.android.jobqueue;
        
        import java.util.ArrayList;
        import java.util.Collection;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.Map;
        import java.util.TreeSet;

/**
 * a util class that holds running jobs sorted by name and unique.
 * it behaves like CopyOnWriteLists
 */
public class RunningJobSet {
    private ArrayList<String> publicClone;
    private final TreeSet<String> internalSet;
    private final Map<String, Long> groupDelays;
    private long groupDelayTimeout;
    
    public RunningJobSet() {
        internalSet = new TreeSet<>();
        groupDelays = new HashMap<>();
        groupDelayTimeout = Long.MAX_VALUE;
    }
    
    public synchronized void addGroupUntil(String group, long until) {
        Long current = groupDelays.get(group);
        if (current != null) {
            if (current > until) {
                return; // nothing to change
            }
        }
        groupDelays.put(group, until);
        groupDelayTimeout = calculateNextDelayForGroups();
        publicClone = null;
    }
    
    public synchronized Collection<String> getSafe() {
        final long now = now();
        if(publicClone == null || now > groupDelayTimeout) {
            if (groupDelays.isEmpty()) {
                publicClone = new ArrayList<>(internalSet);
                groupDelayTimeout = Long.MAX_VALUE;
            } else {
                TreeSet<String> tmpClone = new TreeSet<>(internalSet);
                Iterator<Map.Entry<String, Long>> itr = groupDelays.entrySet().iterator();
                while(itr.hasNext()) {
                    Map.Entry<String, Long> entry = itr.next();
                    if (entry.getValue() > now) {
                        if (!tmpClone.contains(entry.getKey())) {
                            tmpClone.add(entry.getKey());
                        }
                    } else {
                        itr.remove();
                    }
                }
                publicClone = new ArrayList<>(tmpClone);
                groupDelayTimeout = calculateNextDelayForGroups();
            }
        }
        return publicClone;
    }
    public Long getNextDelayForGroups() {
        if (groupDelayTimeout == Long.MAX_VALUE) {
            return null;
        }
        return groupDelayTimeout;
    }
    private long calculateNextDelayForGroups() {
        long result = Long.MAX_VALUE;
        for (Long value : groupDelays.values()) {
            if (value < result) {
                result = value;
            }
        }
        return result;
    }
    
    public synchronized void add(String group) {
        if(internalSet.add(group)) {
            publicClone = null;//invalidate
        }
    }
    
    public synchronized void remove(String group) {
        if(internalSet.remove(group)) {
            publicClone = null;
        }
    }
    
    public synchronized void clear() {
        internalSet.clear();
        groupDelays.clear();
        publicClone = null;
    }
    
    protected long now() {
        return System.nanoTime();
    }
}
