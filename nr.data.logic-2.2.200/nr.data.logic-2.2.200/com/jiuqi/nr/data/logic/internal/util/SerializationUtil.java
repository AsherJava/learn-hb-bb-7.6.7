/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtil {
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(stream).writeObject(object);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Could not serialize object of type: " + object.getClass(), e);
        }
        return stream.toByteArray();
    }

    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return SerializationUtil.deserialize(new ObjectInputStream(new ByteArrayInputStream(bytes)));
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Could not deserialize object", e);
        }
    }

    public static Object deserialize(ObjectInputStream stream) {
        if (stream == null) {
            return null;
        }
        try {
            return stream.readObject();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Could not deserialize object", e);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalStateException("Could not deserialize object type", e);
        }
    }
}

