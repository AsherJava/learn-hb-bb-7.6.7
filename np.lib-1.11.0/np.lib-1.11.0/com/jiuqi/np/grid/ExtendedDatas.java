/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.np.grid;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class ExtendedDatas
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -7135666542860203130L;
    private Map extDatas = new Hashtable();

    public byte[] getData(String name) {
        return (byte[])this.extDatas.get(name);
    }

    public void setData(String name, byte[] data) {
        if (data == null || data.length == 0) {
            this.extDatas.remove(name);
        } else {
            this.extDatas.put(name, data);
        }
    }

    public void setData(String name, int[] data) {
        if (data == null || data.length == 0) {
            this.extDatas.remove(name);
        } else {
            MemStream stream = new MemStream();
            try {
                for (int i = 0; i < data.length; ++i) {
                    stream.writeInt(data[i]);
                }
                this.setData(name, stream.getBytes());
            }
            catch (StreamException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int[] getIntArray(String name) {
        byte[] data = this.getData(name);
        if (data == null || data.length == 0) {
            return null;
        }
        int[] ret = new int[data.length / 4];
        MemStream stream = new MemStream();
        try {
            stream.write(data, 0, data.length);
            stream.seek(0L, 0);
            for (int i = 0; i < ret.length; ++i) {
                ret[i] = stream.readInt();
            }
        }
        catch (StreamException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public void clear() {
        this.extDatas.clear();
    }

    public boolean contains(String name) {
        return this.extDatas.containsKey(name);
    }

    public void loadFromStream(Stream stream, int size) throws StreamException {
        this.clear();
        while (stream.getPosition() < (long)size) {
            int len = stream.readInt();
            String key = stream.readString(len);
            len = stream.readInt();
            byte[] data = new byte[len];
            stream.readBuffer(data, 0, len);
            this.extDatas.put(key, data);
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        for (Map.Entry item : this.extDatas.entrySet()) {
            String key = (String)item.getKey();
            stream.writeInt(key.length());
            stream.writeString(key);
            byte[] data = (byte[])item.getValue();
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
        }
    }

    protected Object clone() {
        try {
            ExtendedDatas result = (ExtendedDatas)super.clone();
            result.extDatas = new Hashtable(this.extDatas);
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

