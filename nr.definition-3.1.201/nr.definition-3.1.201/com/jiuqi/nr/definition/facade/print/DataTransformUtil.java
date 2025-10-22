/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.JqLib
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.definition.facade.print;

import com.jiuqi.bi.util.JqLib;
import com.jiuqi.bi.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataTransformUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataTransformUtil.class);

    public static final byte[] serializeToByteArray(Object obj) {
        byte[] result = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);){
            oos.writeObject(obj);
            result = baos.toByteArray();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static final <T> T deserialize(byte[] byteArray, final Class<T> t) {
        if (byteArray == null) {
            return null;
        }
        Object result = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
             ObjectInputStream ois = new ObjectInputStream(bais){

            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                return t != null && desc.getName().equals(t.getName()) ? t : super.resolveClass(desc);
            }
        };){
            result = ois.readObject();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return (T)result;
    }

    public static final String serializeToString(Object obj) {
        return JqLib.bytesToHexString((byte[])DataTransformUtil.serializeToByteArray(obj));
    }

    public static final <T> T stringDeserialize(String seralizeStr, Class<T> t) {
        if (StringUtils.isEmpty((String)seralizeStr)) {
            return null;
        }
        byte[] bytes = JqLib.hexStringToBytes((String)seralizeStr);
        return DataTransformUtil.deserialize(bytes, t);
    }
}

