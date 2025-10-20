/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nr.definition.util.SerializeList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeListImpl<T>
implements SerializeList<T> {
    private static final Logger logger = LoggerFactory.getLogger(SerializeListImpl.class);
    private Class<T> type;

    public SerializeListImpl(Class<T> type) {
        this.type = type;
    }

    private T[] create(Class<T> type, int size) {
        return (Object[])Array.newInstance(type, size);
    }

    @Override
    public byte[] serialize(List<T> t) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            T[] obj = this.create(this.type, t.size());
            t.toArray(obj);
            out.writeObject(obj);
            out.flush();
            bytes = bos.toByteArray();
            out.close();
            bos.close();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return bytes;
    }

    @Override
    public List<T> deserialize(byte[] bytes, final Class<T> t) {
        ObjectInputStream ois = null;
        List<Object> list = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis){

                @Override
                protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                    return t != null && desc.getName().equals(t) ? t : super.resolveClass(desc);
                }
            };
            Object[] obj = (Object[])ois.readObject();
            list = Arrays.asList(obj);
            ois.close();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }
}

