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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class IntList
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 6119887163753281255L;
    private List list = new ArrayList();

    public int get(int index) {
        return (Integer)this.list.get(index);
    }

    public int indexOf(int value) {
        for (int i = 0; i < this.list.size(); ++i) {
            if ((Integer)this.list.get(i) != value) continue;
            return i;
        }
        return -1;
    }

    public void set(int index, int value) {
        this.list.set(index, new Integer(value));
    }

    public int add(int value) {
        this.list.add(new Integer(value));
        return this.list.size() - 1;
    }

    public void insert(int index, int insCount, int insValue) {
        Integer value = new Integer(insValue);
        for (int i = 0; i < insCount; ++i) {
            this.list.add(index, value);
        }
    }

    public void delete(int index, int delCount) {
        for (int i = 0; i < delCount; ++i) {
            this.list.remove(index);
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        for (int i = 0; i < this.list.size(); ++i) {
            stream.writeInt(this.get(i));
        }
    }

    public void loadFromStream(Stream stream, int size) throws StreamException {
        this.list.clear();
        for (int i = 0; i < size / 4; ++i) {
            this.add(stream.readInt());
        }
    }

    public int count() {
        return this.list.size();
    }

    public void clear() {
        this.list.clear();
    }

    public int[] toArray() {
        if (this.count() == 0) {
            return null;
        }
        int[] result = new int[this.count()];
        for (int i = 0; i < this.count(); ++i) {
            result[i] = this.get(i);
        }
        return result;
    }

    public void sort() {
        Collections.sort(this.list);
    }

    public Iterator iterator() {
        return this.list.iterator();
    }

    public Object clone() {
        try {
            IntList result = (IntList)super.clone();
            result.list = new ArrayList(this.list);
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String toString() {
        return this.list.toString();
    }
}

