/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.np.grid;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.Serializable;

public class GridFontNames
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 7024768019905973473L;
    private int count;
    private String[] names;

    public GridFontNames(int maxCount) {
        this.names = new String[maxCount];
        this.count = 0;
    }

    public int count() {
        return this.count;
    }

    public int addFont(String fontName) {
        for (int i = 0; i < this.count; ++i) {
            if (!this.names[i].equals(fontName)) continue;
            return i;
        }
        if (this.count < this.names.length) {
            this.names[this.count] = fontName;
            ++this.count;
            return this.count - 1;
        }
        this.names[0] = fontName;
        return 0;
    }

    public String getFont(int index) {
        return index >= 0 && index < this.count ? this.names[index] : "";
    }

    public void setFont(int index, String value) {
        if (index >= 0 && index < this.count) {
            this.names[index] = value;
        }
    }

    public int indexOfFont(String fontName) {
        for (int i = 0; i < this.count; ++i) {
            if (!this.names[i].equals(fontName)) continue;
            return i;
        }
        return -1;
    }

    public void loadFromStream(Stream stream) throws StreamException {
        this.count = stream.readInt();
        for (int i = 0; i < this.count; ++i) {
            int ll = stream.read();
            int l = ll < 0 ? 127 + Math.abs(ll) : ll;
            this.names[i] = stream.readString(l);
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        stream.writeInt(this.count);
        for (int i = 0; i < this.count; ++i) {
            byte[] name = stream.encodeString(this.names[i]);
            int len = name.length > 127 ? 127 : name.length;
            stream.write((byte)len);
            stream.writeBuffer(name, 0, len);
        }
    }

    public Object clone() {
        try {
            GridFontNames result = (GridFontNames)super.clone();
            result.names = new String[this.names.length];
            for (int i = 0; i < this.names.length; ++i) {
                result.names[i] = this.names[i];
            }
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

