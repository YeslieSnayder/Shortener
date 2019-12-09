package com.javarush.task.task33.shortener;

import com.javarush.task.task33.shortener.strategy.StorageStrategy;

public class Shortener {
    private Long lastId = 0L;

    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public synchronized String getString(Long id) {
        return storageStrategy.getValue(id);
    }

    public synchronized Long getId(String string) {
        if (storageStrategy.containsValue(string)) return storageStrategy.getKey(string);

        lastId++;
        storageStrategy.put(lastId, string);
        return lastId;
    }
}
