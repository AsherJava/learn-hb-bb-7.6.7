/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.io.ClassResolvingObjectInputStream
 *  org.apache.shiro.session.Session
 */
package com.jiuqi.va.shiro.config.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.apache.shiro.io.ClassResolvingObjectInputStream;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRedisSerializeUtil {
    private static Logger logger = LoggerFactory.getLogger(MyRedisSerializeUtil.class);

    public static Session deserialize(byte[] bytes) {
        Object result = null;
        if (MyRedisSerializeUtil.isEmpty(bytes)) {
            logger.debug("Failed to deserialize, byte is null.");
            return null;
        }
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            try {
                ClassResolvingObjectInputStream objectInputStream = new ClassResolvingObjectInputStream((InputStream)byteStream);
                try {
                    result = objectInputStream.readObject();
                }
                catch (ClassNotFoundException ex) {
                    throw new Exception("Failed to deserialize object type", ex);
                }
            }
            catch (Throwable ex) {
                throw new Exception("Failed to deserialize", ex);
            }
        }
        catch (Exception e) {
            logger.error("Failed to deserialize", e);
        }
        return (Session)result;
    }

    public static boolean isEmpty(byte[] data) {
        return data == null || data.length == 0;
    }

    public static byte[] serialize(Object object) {
        byte[] result = null;
        if (object == null) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
            try {
                if (!(object instanceof Serializable)) {
                    throw new IllegalArgumentException(MyRedisSerializeUtil.class.getSimpleName() + " requires a Serializable payload but received an object of type [" + object.getClass().getName() + "]");
                }
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
                result = byteStream.toByteArray();
            }
            catch (Throwable ex) {
                throw new Exception("Failed to serialize", ex);
            }
        }
        catch (Exception ex) {
            logger.error("Failed to serialize", ex);
        }
        return result;
    }
}

