/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.GridError;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextList
implements Serializable,
Cloneable,
Iterable<String> {
    private static final long serialVersionUID = 1L;
    private List<String> values = new ArrayList<String>();
    private final int capacity;

    public TextList(int capacity) {
        this.capacity = capacity;
    }

    public TextList(int capacity, String ... values) {
        this(capacity);
        for (String value : values) {
            this.values.add(value);
        }
    }

    public int size() {
        return this.values.size();
    }

    public void setSize(int newSize) {
        if (newSize < 0 || newSize > this.capacity) {
            throw new IllegalArgumentException("" + newSize);
        }
        if (newSize < this.values.size()) {
            this.values.subList(newSize, this.values.size()).clear();
        } else if (newSize > this.values.size()) {
            while (this.values.size() < newSize) {
                this.values.add(null);
            }
        }
    }

    public int add(String value) {
        int index = this.indexOf(value);
        if (index >= 0) {
            return index;
        }
        if (this.values.size() < this.capacity) {
            index = this.values.size();
            this.values.add(value);
        } else {
            for (int i = 0; i < this.values.size(); ++i) {
                if (this.values.get(i) != null) continue;
                index = i;
                this.values.set(i, value);
                break;
            }
        }
        return index;
    }

    public String get(int index) {
        if (index >= 0 && index < this.values.size()) {
            return this.values.get(index);
        }
        return null;
    }

    public boolean set(int index, String value) {
        if (index >= 0 && index < this.values.size()) {
            this.values.set(index, value);
            return true;
        }
        return false;
    }

    public int indexOf(String value) {
        return this.values.indexOf(value);
    }

    public void loadFromStream(Stream stream) throws StreamException {
        this.values.clear();
        int size = stream.readInt();
        for (int i = 0; i < size; ++i) {
            byte b = stream.read();
            int len = b & 0xFF;
            if (len == 255) {
                this.values.add(null);
                continue;
            }
            if (len == 0) {
                this.values.add("");
                continue;
            }
            String text = stream.readString(len);
            this.values.add(text);
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        stream.writeInt(this.values.size());
        for (String value : this.values) {
            if (value == null) {
                stream.write((byte)-1);
                continue;
            }
            if (value.isEmpty()) {
                stream.write((byte)0);
                continue;
            }
            byte[] text = stream.encodeString(value);
            int len = text.length > 254 ? 254 : text.length;
            stream.write((byte)len);
            stream.write(text, 0, len);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>(){
            private final Iterator<String> itr;
            {
                this.itr = TextList.this.values.iterator();
            }

            @Override
            public String next() {
                return this.itr.next();
            }

            @Override
            public boolean hasNext() {
                return this.itr.hasNext();
            }
        };
    }

    public Object clone() {
        TextList result;
        try {
            result = (TextList)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new GridError(e);
        }
        result.values = new ArrayList<String>(this.values);
        return result;
    }

    public String toString() {
        return this.values.toString();
    }
}

