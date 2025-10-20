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

public class WordList
implements Serializable {
    private static final long serialVersionUID = -7677681365554217872L;
    private List<Integer> list = new ArrayList<Integer>();

    public int get(int index) {
        return this.list.get(index);
    }

    public void set(int index, int value) {
        this.list.set(index, value);
    }

    public void add(int value) {
        this.list.add(value);
    }

    public void add(Integer value) {
        this.list.add(value);
    }

    public void add(int index, int value) {
        this.list.add(index, value);
    }

    public void delete(int index) {
        this.list.remove(index);
    }

    public void clear() {
        this.list.clear();
    }

    public void loadFromStream(Stream stream, int size) throws StreamException {
        this.list.clear();
        for (int i = 0; i < size / 2; ++i) {
            this.list.add(new Integer(stream.readWord()));
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        for (int i = 0; i < this.list.size(); ++i) {
            Integer v = this.list.get(i);
            stream.writeWord(v == null ? 0 : v);
        }
    }
}

