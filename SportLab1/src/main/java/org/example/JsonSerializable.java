package org.example;

public interface JsonSerializable<T> {
    String toJson();
    T fromJson(String json);
}
