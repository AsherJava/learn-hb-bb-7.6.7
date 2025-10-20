/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.io.BIFFReader;
import com.jiuqi.bi.io.BIFFWriter;
import com.jiuqi.bi.io.IBIFFInput;
import com.jiuqi.bi.io.IBIFFOutput;
import com.jiuqi.bi.types.DataTypes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class Metadata<T>
implements Iterable<Column<T>>,
Cloneable {
    private DataSet<?> owner;
    private List<Column<T>> columns;
    private Map<String, Column<T>> finder;
    private Map<Object, Object> properties;
    private static final int BIFF_FIRST = 10;
    private static final byte BIFF_COLUMN = 11;
    private static final byte BIFF_PEROPERTY = 12;
    private static final byte BIFF_INFOCLASS = 15;

    public Metadata(DataSet<?> owner) {
        this.owner = owner;
        this.columns = new ColumnList();
        this.finder = new HashMap<String, Column<T>>();
    }

    public Metadata() {
        this(null);
    }

    void setOwner(DataSet<T> owner) {
        this.owner = owner;
    }

    public int getColumnCount() {
        return this.columns.size();
    }

    public Column<T> getColumn(int index) {
        return this.columns.get(index);
    }

    public void addColumn(Column<T> column) {
        this.columns.add(column);
    }

    public List<Column<T>> getColumns() {
        return this.columns;
    }

    public boolean removeColumn(Column<T> column) {
        return this.columns.remove(column);
    }

    public boolean removeColumns(Collection<Column<T>> columns) {
        List delCols = columns.stream().filter(col -> this.columns.contains(col)).sorted((c1, c2) -> c1.getIndex() - c2.getIndex()).collect(Collectors.toList());
        if (delCols.isEmpty()) {
            return false;
        }
        if (delCols.size() == this.columns.size()) {
            this.clear();
            return true;
        }
        while (!delCols.isEmpty()) {
            int n;
            Column head = (Column)delCols.get(0);
            for (n = 1; n < delCols.size() && ((Column)delCols.get(n)).getIndex() - ((Column)delCols.get(n - 1)).getIndex() <= 1; ++n) {
            }
            this.columns.subList(head.getIndex(), head.getIndex() + n).clear();
            delCols.subList(0, n).clear();
        }
        return true;
    }

    public int indexOf(String columnName) {
        if (columnName == null) {
            return -1;
        }
        Column<T> col = this.finder.get(columnName.toUpperCase());
        return col == null ? -1 : col.getIndex();
    }

    public Column<T> find(String columnName) {
        if (columnName == null) {
            return null;
        }
        return this.finder.get(columnName.toUpperCase());
    }

    public boolean contains(String columnName) {
        if (columnName == null) {
            return false;
        }
        return this.finder.containsKey(columnName.toUpperCase());
    }

    public Map<Object, Object> getProperties() {
        if (this.properties == null) {
            this.properties = new HashMap<Object, Object>();
        }
        return this.properties;
    }

    public int size() {
        return this.columns.size();
    }

    public void clear() {
        this.columns.clear();
        this.properties = null;
    }

    public boolean compatibleWith(Metadata<?> metadata) {
        if (this.size() != metadata.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); ++i) {
            if (this.getColumns().get(i).getDataType() == metadata.getColumns().get(i).getDataType()) continue;
            return false;
        }
        return true;
    }

    public void copyFrom(Metadata<T> source) {
        this.clear();
        for (Column<T> col : source) {
            this.addColumn((Column<T>)col.clone());
        }
        if (source.properties != null && !source.properties.isEmpty()) {
            this.getProperties().putAll(source.properties);
        }
    }

    @Override
    public Iterator<Column<T>> iterator() {
        return this.columns.iterator();
    }

    public Metadata<T> clone() {
        try {
            Metadata result = (Metadata)super.clone();
            result.setOwner(null);
            result.createColumns(this.columns.size());
            result.finder = new HashMap<String, Column<T>>();
            result.properties = this.properties == null ? null : new HashMap<Object, Object>(this.properties);
            for (Column<T> col : this) {
                result.getColumns().add((Column<T>)col.clone());
            }
            return result;
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    private void createColumns(int initCapacity) {
        this.columns = new ColumnList(initCapacity);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder("[");
        boolean started = false;
        for (Column<T> col : this.columns) {
            if (started) {
                buffer.append(", ");
            } else {
                started = true;
            }
            buffer.append(col.getName()).append(' ').append(DataTypes.dataTypeToString((int)col.getDataType()));
        }
        buffer.append("]");
        return buffer.toString();
    }

    private void doAdd(int start, int count) {
        if (this.owner != null) {
            try {
                this.owner.doAddColumns(start, count);
            }
            catch (DataSetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void doRemove(int start, int count) {
        if (this.owner != null) {
            try {
                this.owner.doRemoveColumns(start, count);
            }
            catch (DataSetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void save(BIFFWriter writer) throws IOException {
        IBIFFOutput out;
        Class<?> infoClass = this.getInfoClass();
        if (infoClass != null) {
            writer.write((byte)15, infoClass.getName());
        }
        for (Column<T> column : this.columns) {
            out = writer.add((byte)11);
            try {
                column.save(out.toWriter());
            }
            finally {
                out.close();
            }
        }
        if (this.properties != null) {
            for (Map.Entry entry : this.properties.entrySet()) {
                if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) continue;
                out = writer.add((byte)12);
                try {
                    BIFFWriter.writeNullEndingString(out.data(), (String)entry.getKey());
                    BIFFWriter.writeNullEndingString(out.data(), (String)entry.getValue());
                }
                finally {
                    out.close();
                }
            }
        }
    }

    private Class<?> getInfoClass() {
        if (this.owner == null || this.owner.infoClass == null) {
            Set classes = this.columns.stream().filter(col -> col.getInfo() != null).map(col -> col.getInfo().getClass()).filter(k -> k != Object.class).collect(Collectors.toSet());
            return classes.size() == 1 ? (Class)classes.iterator().next() : null;
        }
        return this.owner.infoClass;
    }

    void load(BIFFReader reader) throws IOException {
        Class<Object> infoClass;
        this.columns.clear();
        if (this.properties != null) {
            this.properties.clear();
        }
        Class<Object> clazz = infoClass = this.owner == null ? null : this.owner.infoClass;
        while (reader.hasNext()) {
            IBIFFInput in = reader.next();
            if (in.sign() == 11) {
                Column col = new Column();
                col.load(in.toReader(), infoClass);
                this.columns.add(col);
                continue;
            }
            if (in.sign() == 12) {
                String key = BIFFReader.readNullEndingString(in.data());
                String value = BIFFReader.readNullEndingString(in.data());
                this.getProperties().put(key, value);
                continue;
            }
            if (in.sign() != 15) continue;
            String className = in.readString();
            if (infoClass == null) {
                try {
                    infoClass = Class.forName(className);
                    continue;
                }
                catch (ClassNotFoundException e) {
                    throw new IOException(e);
                }
            }
            if (infoClass.getName().equals(className)) continue;
            throw new IOException("\u6570\u636e\u96c6\u5143\u6570\u636e\u52a0\u8f7d\u51fa\u9519\uff0c\u5217\u9644\u52a0\u4fe1\u606f\u7c7b\u578b'" + infoClass.getName() + "'\u4e0e\u5b58\u50a8\u7c7b\u578b\u4e0d\u4e00\u81f4\uff1a" + className);
        }
    }

    private final class ColumnList
    extends ArrayList<Column<T>> {
        private static final long serialVersionUID = 2917341333090598570L;

        public ColumnList() {
        }

        public ColumnList(int initialCapacity) {
            super(initialCapacity);
        }

        @Override
        public boolean add(Column<T> o) {
            if (super.add(o)) {
                o.setIndex(this.size() - 1);
                if (!Metadata.this.finder.containsKey(o.getName().toUpperCase())) {
                    Metadata.this.finder.put(o.getName().toUpperCase(), o);
                }
                Metadata.this.doAdd(o.getIndex(), 1);
                return true;
            }
            return false;
        }

        @Override
        public void add(int index, Column<T> element) {
            for (int i = index; i < Metadata.this.columns.size(); ++i) {
                ((Column)this.get(i)).setIndex(i + 1);
            }
            element.setIndex(index);
            super.add(index, element);
            Metadata.this.finder.put(element.getName().toUpperCase(), element);
            Metadata.this.doAdd(index, 1);
        }

        @Override
        public boolean addAll(Collection<? extends Column<T>> c) {
            int start = this.size();
            if (super.addAll(c)) {
                for (int i = start; i < this.size(); ++i) {
                    ((Column)this.get(i)).setIndex(i);
                    Metadata.this.finder.put(((Column)this.get(i)).getName().toUpperCase(), this.get(i));
                }
                Metadata.this.doAdd(start, c.size());
                return true;
            }
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Column<T>> c) {
            int start = this.size();
            if (super.addAll(index, c)) {
                for (int i = start; i < this.size(); ++i) {
                    ((Column)this.get(i)).setIndex(i);
                }
                for (Column col : c) {
                    Metadata.this.finder.put(col.getName().toUpperCase(), col);
                }
                Metadata.this.doAdd(start, c.size());
                return true;
            }
            return false;
        }

        @Override
        public Column<T> remove(int index) {
            Column item = (Column)super.remove(index);
            for (int i = index; i < this.size(); ++i) {
                ((Column)this.get(i)).setIndex(i);
            }
            Metadata.this.finder.remove(item.getName().toUpperCase());
            Metadata.this.doRemove(index, 1);
            return item;
        }

        @Override
        public boolean remove(Object o) {
            int index = this.indexOf(o);
            if (index == -1) {
                return false;
            }
            this.remove(index);
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void removeRange(int fromIndex, int toIndex) {
            int i;
            for (i = fromIndex; i < toIndex; ++i) {
                Metadata.this.finder.remove(((Column)this.get(i)).getName().toUpperCase());
            }
            super.removeRange(fromIndex, toIndex);
            for (i = fromIndex; i < this.size(); ++i) {
                ((Column)this.get(i)).setIndex(i);
            }
            Metadata.this.doRemove(fromIndex, toIndex - fromIndex);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            if (Metadata.this.owner != null) {
                try {
                    Metadata.this.owner.clear();
                }
                catch (DataSetException e) {
                    throw new RuntimeException(e);
                }
            }
            super.clear();
            Metadata.this.finder.clear();
        }
    }
}

