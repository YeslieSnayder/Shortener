package com.javarush.task.task33.shortener;

import com.javarush.task.task33.shortener.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        StorageStrategy strategy1 = new HashMapStorageStrategy();
        testStrategy(strategy1, 10000);

//        StorageStrategy strategy2 = new OurHashMapStorageStrategy();
//        testStrategy(strategy2, 10000);

//        StorageStrategy strategy3 = new FileStorageStrategy();
//        testStrategy(strategy3, 100);

        StorageStrategy strategy4 = new OurHashBiMapStorageStrategy();
        testStrategy(strategy4, 10000);

        StorageStrategy strategy5 = new HashBiMapStorageStrategy();
        testStrategy(strategy5, 10000);

        StorageStrategy strategy6 = new DualHashBidiMapStorageStrategy();
        testStrategy(strategy6, 10000);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> set = new HashSet<>();
        for (String str : strings) {
            set.add(shortener.getId(str));
        }
        return set;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> set = new HashSet<>();
        for (Long key : keys) {
            set.add(shortener.getString(key));
        }
        return set;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());

        Set<String> generatingStrings = new HashSet<>();
        for (int i = 0; i < elementsNumber; i++) {
            generatingStrings.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);

        Date begin = new Date();
        Set<Long> ids = getIds(shortener, generatingStrings);
        Date end = new Date();
        Helper.printMessage("Время выполнения метода getIds = " + (end.getTime() - begin.getTime()) + " ms.");

        begin = new Date();
        Set<String> values = getStrings(shortener, ids);
        end = new Date();
        Helper.printMessage("Время выполнения метода getStrings = " + (end.getTime() - begin.getTime()) + " ms.");

        if (generatingStrings.equals(values))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");
    }
}
