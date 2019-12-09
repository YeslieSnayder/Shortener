package com.javarush.task.task33.shortener.strategy;

public interface StorageStrategy {
    /**
     * Должен вернуть true, если хранилище содержит переданный ключ.
     * @param key (@code Long) - ключ в хранилище, реализующий интерфейс
     * @return true - хранилище содержит переданный ключ
     */
    boolean containsKey(Long key);

    /**
     * Должен вернуть true, если хранилище содержит переданное значение.
     * @param value (@code String) - значение в хранилище, реализующий интерфейс
     * @return true - хранилище содержит переданное значение.
     */
    boolean containsValue(String value);

    /**
     * Добавляет в хранилище новую пару ключ - значение.
     * @param key Long
     * @param value String
     */
    void put(Long key, String value);

    /**
     * Возвращает идентификатор по значению
     * @param value String - строка в хранилище
     * @return Идентификатор строки
     */
    Long getKey(String value);

    /**
     * Возвращает строку по идентификатору
     * @param id Long - идентификатор строки
     * @return Строку, хранящаяся в хранилище по индивидуальному идентификатору
     */
    String getValue(Long id);
}
