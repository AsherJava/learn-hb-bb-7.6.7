/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.table.df;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.IKey;
import com.jiuqi.nr.table.df.RowFunction;
import com.jiuqi.nr.table.df.Views;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Index
implements Iterable<Object> {
    public static final String DEF_NAME = "_INDEX_";
    public static final String DEF_NAME_ROW = "_ROW_";
    public static final String DEF_NAME_COLUMN = "_COLUMN_";
    private final Map<Object, Integer> index;
    private String[] names;
    private Object[] sources;
    @JsonIgnore
    private Map<Object, Format> keyformatMap = new HashMap<Object, Format>();
    @JsonIgnore
    private Map<String, Format> nameformatMap = new HashMap<String, Format>();

    public void bindFormat(Object key, Format format) {
        this.keyformatMap.put(key, format);
    }

    public void bindFormatForName(String name, Format format) {
        this.nameformatMap.put(name, format);
    }

    public Format getFormat(Object key) {
        return this.keyformatMap.get(key);
    }

    public Format getNameFormat(String name) {
        return this.nameformatMap.get(name);
    }

    public Index() {
        this(Collections.emptyList(), DEF_NAME, 0);
    }

    public Index(int size) {
        this(Collections.emptyList(), DEF_NAME, size);
    }

    public Index(String name) {
        this(new String[]{name});
    }

    public Index(Collection<?> l, String name) {
        this(l, name, l.size());
    }

    public Index(Collection<?> level, String name, int size) {
        this.index = new LinkedHashMap<Object, Integer>(level.size());
        this.names = new String[]{name};
        this.initLevels(level, size);
    }

    public Index(Collection<?> level, String name, Object source, int size) {
        this.index = new LinkedHashMap<Object, Integer>(level.size());
        this.names = new String[]{name};
        this.sources = new Object[]{source};
        this.initLevels(level, size);
    }

    public Index(String[] names) {
        this(new ArrayList(), names, null, 0);
    }

    public Index(Collection<?> levels, String[] names) {
        this(levels, names, null, levels.size());
    }

    public Index(Collection<?> levels, String[] names, Object[] sources) {
        this(levels, names, sources, levels.size());
    }

    public Index(Collection<?> levels, String[] names, Object[] sources, int size) {
        String[] stringArray;
        this.index = new LinkedHashMap<Object, Integer>(levels.size());
        if (names.length == 0) {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = DEF_NAME_ROW;
        } else {
            stringArray = names;
        }
        this.names = stringArray;
        this.sources = sources;
        this.initLevels(levels, size);
    }

    private void initLevels(Collection<?> levels, int size) {
        int i = 0;
        for (Object key : levels) {
            this.add(key, i++);
        }
        if (i < size) {
            while (i < size) {
                this.add(i, i);
                ++i;
            }
        }
    }

    public String[] getNames() {
        return this.names;
    }

    public String getName(int i) {
        if (i > this.count()) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + this.count());
        }
        return this.getNames()[i];
    }

    public Object[] getSources() {
        return this.sources;
    }

    public void setSources(Object[] sources) {
        this.sources = sources;
    }

    public void add(Object key, Integer num) {
        if (this.index.put(key, num) != null) {
            throw new IllegalArgumentException("duplicate key '" + key + "' in index");
        }
    }

    public Integer get(Object key) {
        Integer i = this.index.get(key);
        if (i == null) {
            throw new IllegalArgumentException("key '" + key + "' not in index");
        }
        return i;
    }

    public Object getKey(Integer i) {
        for (Map.Entry<Object, Integer> v : this.index.entrySet()) {
            if (!v.getValue().equals(i)) continue;
            return v.getKey();
        }
        return null;
    }

    public void add(Object key) {
        this.add(key, this.size());
    }

    public void fill() {
        this.add(this.size(), this.size());
    }

    public void add(IKey key) {
        this.add(key, this.size());
    }

    public void extend(Integer size) {
        for (int i = this.index.size(); i < size; ++i) {
            this.add(i, i);
        }
    }

    public void set(Object key, Integer value) {
        this.index.put(key, value);
    }

    public Set<Object> levels() {
        return this.index.keySet();
    }

    public Integer[] indices(List<Object> levels) {
        int size = levels.size();
        Integer[] indices = new Integer[size];
        for (int i = 0; i < size; ++i) {
            indices[i] = this.get(levels.get(i));
        }
        return indices;
    }

    public Integer[] indices(Object[] levels) {
        return this.indices(Arrays.asList(levels));
    }

    public void remove(Object ... keys) {
        for (Object o : keys) {
            this.index.remove(o);
        }
    }

    public int size() {
        return this.index.size();
    }

    public int count() {
        Optional first = this.index.entrySet().stream().findAny();
        if (first.isPresent()) {
            Object key = ((Map.Entry)first.get()).getKey();
            if (key instanceof IKey) {
                return ((IKey)key).length();
            }
            return 1;
        }
        return 0;
    }

    @JsonIgnore
    public boolean isMultIndex() {
        return this.count() > 1;
    }

    @Override
    public Iterator<Object> iterator() {
        return this.index.keySet().iterator();
    }

    public static Index fromArray(List<?> index1, List<?> index2, String name1, String name2) {
        Index index = new Index(new String[]{name1, name2});
        for (int i = 0; i < index1.size() && i < index2.size(); ++i) {
            IKey values = IKey.newIKey(index1.get(i), index1.get(i));
            index.add(values);
        }
        return index;
    }

    public static Index fromProduct(List<?> index1, List<?> index2, String name1, String name2) {
        Index index = new Index(new String[]{name1, name2});
        for (int i = 0; i < index1.size(); ++i) {
            for (int j = 0; j < index2.size(); ++j) {
                IKey values = IKey.newIKey(index1.get(i), index2.get(j));
                index.add(values);
            }
        }
        return index;
    }

    public static <E> DataFrame<E> reset(DataFrame<E> df) {
        ArrayList<IKey> index = new ArrayList<IKey>(df.length());
        for (int i = 0; i < df.length(); ++i) {
            index.add(IKey.newIKey(i));
        }
        ArrayList<Object> columns = new ArrayList<Object>(df.columns().levels());
        return new DataFrame(index, columns, new Views.ListView<E>(df, false));
    }

    public static <E> DataFrame<E> reindex(DataFrame<E> df, final Integer ... cols) {
        return new DataFrame(df.transform(cols.length == 1 ? new RowFunction<E, Object>(){

            @Override
            public List<List<Object>> apply(List<E> values) {
                return Collections.singletonList(Collections.singletonList(values.get(cols[0])));
            }
        } : new RowFunction<E, Object>(){

            @Override
            public List<List<Object>> apply(List<E> values) {
                ArrayList key = new ArrayList(cols.length);
                Integer[] integerArray = cols;
                int n = integerArray.length;
                for (int i = 0; i < n; ++i) {
                    int c = integerArray[i];
                    key.add(values.get(c));
                }
                return Collections.singletonList(Collections.singletonList(Collections.unmodifiableList(key)));
            }
        }).col(0), df.columns().levels(), new Views.ListView<E>(df, false));
    }

    public String toString() {
        return String.join((CharSequence)".", this.names);
    }

    public void copyMeta(Index other) {
        this.names = (String[])other.names.clone();
        if (null != other.sources) {
            this.sources = (Object[])other.sources.clone();
        }
        this.keyformatMap = other.keyformatMap;
        this.nameformatMap = other.nameformatMap;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

