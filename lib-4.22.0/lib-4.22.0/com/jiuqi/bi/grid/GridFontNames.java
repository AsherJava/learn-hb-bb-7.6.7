/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GridFontNames
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 7024768019905973473L;
    private List<String> names = new ArrayList<String>();
    private int capacity;

    public GridFontNames(int maxCount) {
        this.capacity = maxCount;
    }

    public int count() {
        return this.names.size();
    }

    public int addFont(String fontName) {
        int index = this.names.indexOf(fontName);
        if (index < 0) {
            if (this.names.size() < this.capacity) {
                index = this.names.size();
                this.names.add(fontName);
            } else {
                index = 0;
            }
        }
        return index;
    }

    public String getFont(int index) {
        return index >= 0 && index < this.names.size() ? this.names.get(index) : "";
    }

    public void setFont(int index, String value) {
        if (index >= 0 && index < this.names.size()) {
            this.names.set(index, value);
        }
    }

    public int indexOfFont(String fontName) {
        return this.names.indexOf(fontName);
    }

    public void loadFromStream(Stream stream) throws StreamException {
        this.names.clear();
        int count = stream.readInt();
        for (int i = 0; i < count; ++i) {
            int len = stream.read();
            int l = len < 0 ? 127 + Math.abs(len) : len;
            String name = stream.readString(l);
            this.names.add(name);
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        stream.writeInt(this.names.size());
        for (int i = 0; i < this.names.size(); ++i) {
            byte[] name = stream.encodeString(this.names.get(i));
            int len = name.length > 127 ? 127 : name.length;
            stream.write((byte)len);
            stream.writeBuffer(name, 0, len);
        }
    }

    public Object clone() {
        GridFontNames result;
        try {
            result = (GridFontNames)super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
        result.names = new ArrayList<String>(this.names);
        return result;
    }

    public String toString() {
        return this.names.toString();
    }
}

