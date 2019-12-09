package com.javarush.task.task33.shortener.strategy;

import com.javarush.task.task33.shortener.Helper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile(Helper.generateRandomString(), Helper.generateRandomString());
            Files.deleteIfExists(path);
            Files.createFile(path);
            path.toFile().deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return 0;
        }
    }

    public void putEntry(Entry entry) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))){
            outputStream.writeObject(entry.next);
            outputStream.writeObject(entry);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public Entry getEntry() {
        if (getFileSize() <= 0) return null;

        Entry entry = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))){
            Entry next = (Entry) inputStream.readObject();
            entry = (Entry) inputStream.readObject();
            entry.next = next;
        } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }

        return entry;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
