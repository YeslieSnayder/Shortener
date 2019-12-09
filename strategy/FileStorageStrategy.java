package com.javarush.task.task33.shortener.strategy;

public class FileStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;

    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];

    int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize;

    public int hash(Long k) {
        int h;
        return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) {
        FileBucket[] tab; FileBucket firstBucket; Entry first, e; int n;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (firstBucket = tab[(n - 1) & hash(key)]) != null) {
            first = firstBucket.getEntry();
            // always check first node
            if (first.hash == hash(key) && first.key.equals(key))
                return first;
            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash(key) && e.key.equals(key))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    public void resize(int newCapacity) {
        FileBucket[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == DEFAULT_INITIAL_CAPACITY) {
            return;
        }

        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
    }

    public void transfer(FileBucket[] newTable) {
        FileBucket[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            FileBucket bucket = src[j];
            Entry e = bucket.getEntry();
            bucket.remove();
            if (e != null) {
                src[j] = null;
                do {
                    Entry next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i].getEntry();
                    FileBucket newBucket = new FileBucket();
                    newBucket.putEntry(e);
                    newTable[i] = newBucket;
                    e = next;
                } while (e != null);
            }
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        FileBucket e = table[bucketIndex];
        Entry entry;
        try {
            Entry next = e.getEntry();
            entry = new Entry(hash, key, value, next);
        } catch (NullPointerException ex) {
            entry = new Entry(hash, key, value, null);
        }

        FileBucket bucket = new FileBucket();
        bucket.putEntry(entry);
        table[bucketIndex] = bucket;
        size++;
        for (FileBucket b : table) {
            long fileSize;
            try {
                fileSize = b.getFileSize();
            } catch (NullPointerException ex) {
                fileSize = 0;
            }
            if (fileSize > bucketSizeLimit)
                resize( 2 * table.length );
        }
    }

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (table != null && size > 0) {
            for (FileBucket bucket : table) {
                Entry entry;
                try {
                    entry = bucket.getEntry();
                } catch (NullPointerException ex) {
                    entry = null;
                }
                for (Entry e = entry; e != null; e = e.next) {
                    if (e.value.equals(value))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        FileBucket bucket = table[index];
        Entry e = null;
        if (bucket != null) e = bucket.getEntry();
        while (e != null) {
            if (e.hash == hash && key.equals(e.key)) {
                e.value = value;
            }
            e = e.next;
        }

        size++;
        addEntry(hash, key, value, index);
    }

    @Override
    public Long getKey(String value) {
        if (!containsValue(value)) return null;

        for (FileBucket bucket : table) {
            Entry e = bucket.getEntry();
            if (e.value.equals(value))
                return e.key;
        }
        return null;
    }

    @Override
    public String getValue(Long id) {
        if (!containsKey(id)) return null;

        return getEntry(id).getValue();
    }
}
