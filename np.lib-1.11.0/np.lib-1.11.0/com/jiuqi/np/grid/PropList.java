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
import com.jiuqi.np.grid.GridCell;
import com.jiuqi.np.grid.NumberCellPropertyIntf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropList
implements Serializable {
    private static final long serialVersionUID = 1448569612032369073L;
    private List list = new ArrayList();
    private Map finder = new HashMap();
    private int bufSize;
    private Key tmpKey;

    public PropList(int bufferSize) {
        this.bufSize = bufferSize;
        this.tmpKey = new Key();
    }

    public byte[] get(int index) {
        return (byte[])this.list.get(index);
    }

    public void set(int index, byte[] value) {
        this.finder.remove(this.tmpKey.setProperty((byte[])this.list.get(index)));
        byte[] b = (byte[])this.list.get(index);
        System.arraycopy(value, 0, b, 0, this.bufSize);
        this.finder.put(new Key(b), new Integer(index));
    }

    public int indexOf(byte[] propData) {
        Integer index = (Integer)this.finder.get(this.tmpKey.setProperty(propData));
        return index == null ? -1 : index;
    }

    public int count() {
        return this.list.size();
    }

    public void setCount(int value) {
        int i;
        for (i = this.list.size(); i < value; ++i) {
            this.list.add(new byte[this.bufSize]);
        }
        for (i = this.list.size() - 1; i >= value; --i) {
            this.list.remove(i);
        }
    }

    public int add(byte[] propData) {
        byte[] b = new byte[this.bufSize];
        System.arraycopy(propData, 0, b, 0, this.bufSize);
        int index = this.list.size();
        this.list.add(b);
        this.finder.put(new Key(b), new Integer(index));
        return index;
    }

    private void rebuild() {
        this.finder.clear();
        for (int i = 0; i < this.list.size(); ++i) {
            this.finder.put(new Key((byte[])this.list.get(i)), new Integer(i));
        }
    }

    public void loadFromStream(Stream stream, int size) throws StreamException {
        this.list.clear();
        for (int i = 0; i < size / this.bufSize; ++i) {
            byte[] b = new byte[this.bufSize];
            stream.readBuffer(b, 0, this.bufSize);
            this.list.add(b);
        }
        this.rebuild();
    }

    public void loadFromStreamEx(Stream stream, int bufStart, int bufLen) throws StreamException {
        int count = (int)(stream.getSize() / (long)bufLen);
        this.setCount(count);
        stream.setPosition(0L);
        for (int i = 0; i < count; ++i) {
            byte[] buf = (byte[])this.list.get(i);
            stream.readBuffer(buf, bufStart, bufLen);
        }
        this.rebuild();
    }

    public void saveToStream(Stream stream) throws StreamException {
        for (int i = 0; i < this.list.size(); ++i) {
            byte[] b = (byte[])this.list.get(i);
            stream.writeBuffer(b, 0, this.bufSize);
        }
    }

    public void saveToStreamEx(Stream stream, int bufStart, int bufLen) throws StreamException {
        for (int i = 0; i < this.list.size(); ++i) {
            byte[] buf = (byte[])this.list.get(i);
            stream.writeBuffer(buf, bufStart, bufLen);
        }
    }

    public void remove(int index) {
        this.list.remove(index);
        this.rebuild();
    }

    public void updateToVer12() {
        GridCell cell = new GridCell();
        boolean needRebuild = false;
        for (byte[] prop : this.list) {
            int decimal;
            NumberCellPropertyIntf number;
            cell.internalInit(null, 0, 0, prop);
            if (cell.getDataType() != 2 || !(number = cell.toNumberCell()).getIsPercent()) continue;
            int n = decimal = number.getThoundsMark() ? number.getDecimal() - 3 : number.getDecimal() - 2;
            if (decimal < 0) {
                decimal = 0;
            }
            number.setDecimal(decimal);
            byte[] newProp = cell.getPropData();
            System.arraycopy(newProp, 0, prop, 0, prop.length);
            needRebuild = true;
        }
        if (needRebuild) {
            this.rebuild();
        }
    }

    private static final class Key
    implements Serializable {
        private static final long serialVersionUID = -209685468789117023L;
        private byte[] property;
        private int hash;

        public Key() {
        }

        public Key(byte[] property) {
            this.setProperty(property);
        }

        public Key setProperty(byte[] property) {
            this.property = property;
            if (property == null) {
                this.hash = 0;
            } else {
                this.hash = 1;
                for (int i = 0; i < property.length; ++i) {
                    this.hash = 31 * this.hash + property[i];
                }
            }
            return this;
        }

        public int hashCode() {
            return this.hash;
        }

        public boolean equals(Object obj) {
            Key another = (Key)obj;
            if (this.hash != another.hash) {
                return false;
            }
            for (int i = 0; i < this.property.length; ++i) {
                if (this.property[i] == another.property[i]) continue;
                return false;
            }
            return true;
        }

        public String toString() {
            StringBuffer s = new StringBuffer();
            s.append(this.property).append('(').append(this.hash).append(')');
            return s.toString();
        }
    }
}

