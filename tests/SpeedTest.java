package com.javarush.task.task33.shortener.tests;

import com.javarush.task.task33.shortener.Helper;
import com.javarush.task.task33.shortener.Shortener;
import com.javarush.task.task33.shortener.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.shortener.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.shortener.strategy.StorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class SpeedTest {

    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date begin = new Date();
        for (String s : strings) {
            ids.add(shortener.getId(s));
        }
        Date end = new Date();

        return end.getTime() - begin.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date begin = new Date();
        for (Long id : ids) {
            strings.add(shortener.getString(id));
        }
        Date end = new Date();

        return end.getTime() - begin.getTime();
    }

    @Test
    public void testHashMapStorage() {
        StorageStrategy strategy1 = new HashMapStorageStrategy();
        StorageStrategy strategy2 = new HashBiMapStorageStrategy();
        Shortener shortener1 = new Shortener(strategy1);
        Shortener shortener2 = new Shortener(strategy2);

        Set<String> origStrings = new TreeSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> ids1 = new TreeSet<>();
        Set<Long> ids2 = new TreeSet<>();

        long timeToGetIdsForHashMap = getTimeToGetIds(shortener1, origStrings, ids1);
        long timeToGetIdsForHashBiMap = getTimeToGetIds(shortener2, origStrings, ids2);
        Assert.assertTrue(timeToGetIdsForHashBiMap < timeToGetIdsForHashMap);

        Set<String> strings1 = new TreeSet<>();
        Set<String> strings2 = new TreeSet<>();

        long timeToGetStringsHashMap = getTimeToGetStrings(shortener1, ids1, strings1);
        long timeToGetStringsHashBiMap = getTimeToGetStrings(shortener2, ids2, strings2);
        Assert.assertEquals(timeToGetStringsHashMap, timeToGetStringsHashBiMap, 30);
    }
}
