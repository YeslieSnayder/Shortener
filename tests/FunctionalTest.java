package com.javarush.task.task33.shortener.tests;

import com.javarush.task.task33.shortener.Helper;
import com.javarush.task.task33.shortener.Shortener;
import com.javarush.task.task33.shortener.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.shortener.strategy.JDBCMapStorageStrategy;
import com.javarush.task.task33.shortener.strategy.StorageStrategy;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {

    public void testStorage(Shortener shortener) {
        String str1 = Helper.generateRandomString();
        String str2 = Helper.generateRandomString();
        String str3 = new String(str1);

        Long id1 = shortener.getId(str1);
        Long id2 = shortener.getId(str2);
        Long id3 = shortener.getId(str3);

        Assert.assertNotEquals(id2, id1);
        Assert.assertNotEquals(id2, id3);

        Assert.assertEquals(id1, id3);

        String newStr1 = shortener.getString(id1);
        String newStr2 = shortener.getString(id2);
        String newStr3 = shortener.getString(id3);

        Assert.assertEquals(str1, newStr1);
        Assert.assertEquals(str2, newStr2);
        Assert.assertEquals(str3, newStr3);
    }

    @Test
    public void testHashMapStorageStrategy() {
        StorageStrategy strategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        StorageStrategy strategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy() {
        StorageStrategy strategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        StorageStrategy strategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        StorageStrategy strategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        StorageStrategy strategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testJDBC() {
        StorageStrategy strategy = new JDBCMapStorageStrategy();
        Helper.printMessage(String.valueOf(strategy.containsKey(30L)));
    }
}
