/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid2.MemStream2
 *  com.jiuqi.np.grid2.ReadMemStream2
 *  com.jiuqi.np.grid2.Stream2
 *  com.jiuqi.np.grid2.StreamException2
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.grid2.MemStream2;
import com.jiuqi.np.grid2.ReadMemStream2;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesignFormDefineBigDataUtil
implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(DesignFormDefineBigDataUtil.class);

    public static byte[] StringToBytes(String data) {
        if (data == null) {
            return null;
        }
        MemStream2 store = new MemStream2();
        try {
            DesignFormDefineBigDataUtil.saveToStream((Stream2)store, data);
            return store.getBytes();
        }
        catch (StreamException2 ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static void saveToStream(Stream2 stream, String strData) throws StreamException2 {
        byte[] data = stream.encodeString(strData == null ? "" : strData);
        stream.writeInt(data.length);
        stream.writeBuffer(data, 0, data.length);
    }

    public static String bytesToString(byte[] data) {
        if (data == null) {
            return null;
        }
        String strData = null;
        try {
            ReadMemStream2 s = new ReadMemStream2();
            s.writeBuffer(data, 0, data.length);
            s.setPosition(0L);
            strData = DesignFormDefineBigDataUtil.loadFromStream((Stream2)s);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return strData;
    }

    public static String loadFromStream(Stream2 stream) throws StreamException2 {
        int length = stream.readInt();
        String data = stream.readString(length);
        return data;
    }
}

