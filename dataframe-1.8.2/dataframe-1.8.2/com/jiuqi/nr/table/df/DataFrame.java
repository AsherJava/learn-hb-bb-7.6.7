/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import com.jiuqi.nr.table.df.BlockData;
import com.jiuqi.nr.table.df.Combining;
import com.jiuqi.nr.table.df.IKey;
import com.jiuqi.nr.table.df.Index;
import com.jiuqi.nr.table.df.Inspection;
import com.jiuqi.nr.table.df.KeyFunction;
import com.jiuqi.nr.table.df.RowFunction;
import com.jiuqi.nr.table.df.Selection;
import com.jiuqi.nr.table.df.Shaping;
import com.jiuqi.nr.table.df.Sorting;
import com.jiuqi.nr.table.df.Views;
import com.jiuqi.nr.table.io.DataFrameReader;
import com.jiuqi.nr.table.io.DataFrameWriter;
import com.jiuqi.nr.table.io.Serialization;
import com.zaxxer.sparsebits.SparseBitSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class DataFrame<E>
implements Iterable<List<E>> {
    private Index rows;
    private Index columns;
    private BlockData<E> data;
    private String name;

    public DataFrame() {
        this(Collections.emptyList());
    }

    public DataFrame(String ... columns) {
        this((Collection<?>)Arrays.asList((Object[])columns));
    }

    public DataFrame(Collection<?> columns) {
        this(Collections.emptyList(), columns, Collections.emptyList());
    }

    public DataFrame(Collection<?> index, Collection<?> columns) {
        this(index, columns, Collections.emptyList());
    }

    public DataFrame(List<List<E>> data) {
        this(Collections.emptyList(), Collections.emptyList(), data);
    }

    public DataFrame(Collection<?> rows, Collection<?> columns, List<List<E>> data) {
        BlockData<E> bd = new BlockData<E>(data);
        this.data = bd;
        this.rows = new Index(rows, "_ROW_", bd.size());
        this.columns = new Index(columns, "_COLUMN_", bd.length());
    }

    public DataFrame(Index columns) {
        this.data = new BlockData(Collections.emptyList());
        this.columns = columns;
        this.rows = new Index();
    }

    public DataFrame(Index rows, Index columns, BlockData<E> data) {
        this.data = data;
        this.data.reshape(Math.max(data.size(), rows.size()), Math.max(data.length(), columns.size()));
        this.rows = rows;
        this.columns = columns;
    }

    public static DataFrame<Object> create(String name) {
        DataFrame<Object> df = new DataFrame<Object>();
        df.setName(name);
        return df;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Index rows() {
        return this.rows;
    }

    public Index columns() {
        return this.columns;
    }

    public BlockData<E> getData() {
        return this.data;
    }

    public void unique() {
    }

    public E get(IKey col, IKey row) {
        return this.get(this.columns.get(col), this.rows.get(col));
    }

    public E get(int col, int row) {
        return this.data.get(col, row);
    }

    public void set(Integer col, Integer row, E value) {
        this.data.set(col, row, value);
    }

    public List<E> row(Object row) {
        return this.row(this.rows.get(row));
    }

    public List<E> row(Integer row) {
        Views.SeriesListView seriesListView = new Views.SeriesListView(this, row, false);
        return seriesListView;
    }

    public List<E> col(Object column) {
        return this.col(this.columns.get(column));
    }

    public List<E> col(Integer column) {
        return new Views.SeriesListView(this, column, true);
    }

    public int length() {
        return this.data.length();
    }

    public boolean isEmpty() {
        return this.length() == 0;
    }

    public int size() {
        return this.data.size();
    }

    public int rowSize() {
        return this.data.rowSize();
    }

    public int columnSize() {
        return this.data.colSize();
    }

    public DataFrame<E> add(Object ... rows) {
        for (Object row : rows) {
            ArrayList values = new ArrayList(this.length());
            for (int r = 0; r < values.size(); ++r) {
                values.add(null);
            }
            this.add(row, values);
        }
        return this;
    }

    public DataFrame<E> add(Object row, List<E> values) {
        int rowSize = this.size();
        this.rows.add(row, rowSize);
        this.columns.extend(values.size());
        this.data.add(values);
        return this;
    }

    public DataFrame<E> add(List<E> values) {
        return this.add((Object)this.size(), values);
    }

    public DataFrame<E> remove(List<Object> rowKeys) {
        return this.remove(this.rows.indices(rowKeys));
    }

    public DataFrame<E> remove(Object ... rowKey) {
        return this.remove(this.rows.indices(rowKey));
    }

    public DataFrame<E> remove(Integer ... rowSeqs) {
        ArrayList<Object> rownames = new ArrayList<Object>(this.rows.levels());
        Integer[] integerArray = rowSeqs;
        int n = integerArray.length;
        for (int i = 0; i < n; ++i) {
            int n2 = integerArray[i];
            rownames.remove(n2);
        }
        Index new_rows = new Index(rownames, this.rows.getNames(), this.rows.getSources());
        ArrayList<List<List<E>>> keep = new ArrayList<List<List<E>>>(rownames.size());
        for (Object e : rownames) {
            keep.add(this.row(e));
        }
        return new DataFrame(new_rows, this.columns, new BlockData(keep));
    }

    public DataFrame<E> drop(List<Object> cols) {
        return this.drop(this.columns.indices(cols));
    }

    public DataFrame<E> drop(Object ... colKey) {
        return this.drop(this.columns.indices(colKey));
    }

    public DataFrame<E> drop(Integer ... colSeqs) {
        ArrayList<Object> colnames = new ArrayList<Object>(this.columns.levels());
        Integer[] integerArray = colSeqs;
        int n = integerArray.length;
        for (int i = 0; i < n; ++i) {
            int n2 = integerArray[i];
            colnames.remove(n2);
        }
        Index new_columns = new Index(colnames, this.columns.getNames(), this.columns.getSources());
        BlockData<E> blockData = new BlockData<E>(this.rows.size());
        for (Object e : colnames) {
            blockData.addCol(this.col(e));
        }
        return new DataFrame(this.rows, new_columns, blockData);
    }

    public DataFrame<E> append(Object ... columns) {
        for (Object column : columns) {
            ArrayList values = new ArrayList(this.length());
            for (int r = 0; r < values.size(); ++r) {
                values.add(null);
            }
            this.append(column, values);
        }
        return this;
    }

    public DataFrame<E> append(IKey name, E[] values) {
        return this.append((Object)name, Arrays.asList(values));
    }

    public DataFrame<E> append(List<? extends E> col) {
        return this.append((Object)this.length(), col);
    }

    public DataFrame<E> append(Object name, List<? extends E> values) {
        int len = this.length();
        this.columns.add(name, len);
        this.rows.extend(values.size());
        this.data.reshape(this.rows.size(), len + 1);
        for (int c = 0; c < this.data.size(); ++c) {
            this.data.set(len, c, c < values.size() ? (Object)values.get(c) : null);
        }
        return this;
    }

    public DataFrame<E> append(Object column, Function<List<E>, E> function) {
        ArrayList<E> values = new ArrayList<E>();
        for (List<E> row : this) {
            values.add(function.apply(row));
        }
        return this.append(column, values);
    }

    public DataFrame<E> reshape(Integer rows, Integer cols) {
        return Shaping.reshape(this, rows, cols);
    }

    public DataFrame<E> reshape(Collection<?> rows, Collection<?> cols) {
        return Shaping.reshape(this, rows, cols);
    }

    public DataFrame<E> resetIndex() {
        return Index.reset(this);
    }

    public List<List<E>> loc(Object ... names) {
        return null;
    }

    public List<Class<?>> types() {
        return Inspection.types(this);
    }

    public List<E> flatten() {
        return new Views.FlatView(this);
    }

    @Override
    public Iterator<List<E>> iterator() {
        return this.iterrows();
    }

    public ListIterator<List<E>> iterrows() {
        return new Views.ListView(this, false).listIterator();
    }

    public ListIterator<List<E>> itercols() {
        return new Views.ListView(this, true).listIterator();
    }

    public ListIterator<E> itervalues() {
        return new Views.FlatView(this).listIterator();
    }

    public DataFrame<E> transpose() {
        return new DataFrame<E>(this.columns.levels(), this.rows.levels(), new Views.ListView(this, true));
    }

    public DataFrame<E> sortBy(Object ... cols) {
        LinkedHashMap<Integer, SortDirection> sortCols = new LinkedHashMap<Integer, SortDirection>();
        for (Object col : cols) {
            String str = col instanceof String ? (String)String.class.cast(col) : "";
            SortDirection dir = str.startsWith("-") ? SortDirection.DESCENDING : SortDirection.ASCENDING;
            int c = this.columns.get(str.startsWith("-") ? str.substring(1) : col);
            sortCols.put(c, dir);
        }
        return Sorting.sort(this, sortCols);
    }

    public DataFrame<E> sortBy(Integer ... cols) {
        LinkedHashMap<Integer, SortDirection> sortCols = new LinkedHashMap<Integer, SortDirection>();
        Integer[] integerArray = cols;
        int n = integerArray.length;
        for (int i = 0; i < n; ++i) {
            int c = integerArray[i];
            SortDirection dir = c < 0 ? SortDirection.DESCENDING : SortDirection.ASCENDING;
            sortCols.put(Math.abs(c), dir);
        }
        return this.sortBy(sortCols);
    }

    public DataFrame<E> sortBy(Map<Integer, SortDirection> sortCols) {
        return Sorting.sort(this, sortCols);
    }

    public DataFrame<E> sortBy(Comparator<List<E>> comparator) {
        return Sorting.sort(this, comparator);
    }

    public final DataFrame<E> join(DataFrame<E> other) {
        return this.join(other, JoinType.LEFT, null);
    }

    public final DataFrame<E> join(DataFrame<E> other, JoinType join) {
        return this.join(other, join, null);
    }

    public final DataFrame<E> join(DataFrame<E> other, KeyFunction<E> on) {
        return this.join(other, JoinType.LEFT, on);
    }

    public final DataFrame<E> join(DataFrame<E> other, JoinType join, KeyFunction<E> on) {
        return Combining.join(this, other, join, on);
    }

    public final DataFrame<E> merge(DataFrame<E> other) {
        return this.merge(other, JoinType.LEFT);
    }

    public final DataFrame<E> merge(DataFrame<E> other, JoinType join) {
        return Combining.merge(this, other, join);
    }

    public DataFrame<E> nonnumeric() {
        SparseBitSet nonnumeric = Inspection.nonnumeric(this);
        Set<Object> keep = Selection.select(this.columns, nonnumeric).levels();
        return this.retain(keep.toArray(new Object[keep.size()]));
    }

    @SafeVarargs
    public final DataFrame<E> coalesce(DataFrame<? extends E> ... others) {
        Combining.update(this, false, others);
        return this;
    }

    @SafeVarargs
    public final DataFrame<E> concat(DataFrame<? extends E> ... others) {
        return Combining.concat(this, others);
    }

    public DataFrame<E> retain(Object ... cols) {
        return this.retain(this.columns.indices(cols));
    }

    public DataFrame<E> retain(Integer ... cols) {
        HashSet<Integer> keep = new HashSet<Integer>(Arrays.asList(cols));
        Integer[] todrop = new Integer[this.size() - keep.size()];
        int i = 0;
        for (int c = 0; c < this.size(); ++c) {
            if (keep.contains(c)) continue;
            todrop[i++] = c;
        }
        return this.drop(todrop);
    }

    public DataFrame<E> reindex(Integer col, boolean drop) {
        DataFrame df = Index.reindex(this, col);
        return drop ? df.drop(col) : df;
    }

    public DataFrame<E> reindex(Integer[] cols, boolean drop) {
        DataFrame df = Index.reindex(this, cols);
        return drop ? df.drop(cols) : df;
    }

    public DataFrame<E> reindex(Integer ... cols) {
        return this.reindex(cols, true);
    }

    public DataFrame<E> reindex(Object col, boolean drop) {
        return this.reindex(this.columns.get(col), drop);
    }

    public DataFrame<E> reindex(Object[] cols, boolean drop) {
        return this.reindex(this.columns.indices(cols), drop);
    }

    public DataFrame<E> reindex(Object ... cols) {
        return this.reindex(this.columns.indices(cols), true);
    }

    public <U> DataFrame<U> apply(Function<E, U> function) {
        return new DataFrame<E>(this.rows.levels(), this.columns.levels(), new Views.TransformedView<E, U>(this, function, false));
    }

    public <U> DataFrame<U> transform(RowFunction<E, U> transform) {
        DataFrame<U> transformed = new DataFrame<U>(this.columns.levels());
        Iterator<Object> it = this.columns().iterator();
        for (List<E> row : this) {
            for (List<U> trans : transform.apply(row)) {
                transformed.add(it.hasNext() ? it.next() : Integer.valueOf(transformed.length()), trans);
            }
        }
        return transformed;
    }

    public DataFrame<E> select(Predicate<List<E>> predicate) {
        SparseBitSet selected = Selection.select(this, predicate);
        return new DataFrame<E>(Selection.select(this.rows, selected), this.columns, Selection.select(this.data, selected));
    }

    public DataFrame<E> slice(Object rowStart, Object rowEnd) {
        return this.slice(this.rows.get(rowStart), this.rows.get(rowEnd), 0, this.size());
    }

    public DataFrame<E> slice(Object rowStart, Object rowEnd, Object colStart, Object colEnd) {
        return this.slice(this.rows.get(rowStart), this.rows.get(rowEnd), this.columns.get(colStart), this.columns.get(colEnd));
    }

    public DataFrame<E> slice(Integer rowStart, Integer rowEnd) {
        return this.slice(rowStart, rowEnd, 0, this.length());
    }

    public DataFrame<E> slice(Integer rowStart, Integer rowEnd, Integer colStart, Integer colEnd) {
        SparseBitSet[] slice = Selection.slice(this, rowStart, rowEnd, colStart, colEnd);
        return new DataFrame<E>(Selection.select(this.rows, slice[0]), Selection.select(this.columns, slice[1]), Selection.select(this.data, slice[0], slice[1]));
    }

    public DataFrame<E> dropna() {
        return this.dropna(Axis.ROWS);
    }

    public DataFrame<E> dropna(Axis direction) {
        switch (direction) {
            case ROWS: {
                return this.select(new Selection.DropNaPredicate());
            }
        }
        return this.transpose().select(new Selection.DropNaPredicate()).transpose();
    }

    public Object[] toArray() {
        return this.toArray(new Object[this.size() * this.length()]);
    }

    public <U> U[] toArray(U[] array) {
        return new Views.FlatView(this).toArray(array);
    }

    public String toString() {
        return this.print();
    }

    public String print() {
        return this.print(10);
    }

    public String print(int rowLimit) {
        return Serialization.toString(this, rowLimit);
    }

    public static DataFrameReader read() {
        return new DataFrameReader();
    }

    public DataFrameWriter write() {
        return new DataFrameWriter(this);
    }

    public static enum NumberDefault {
        LONG_DEFAULT,
        DOUBLE_DEFAULT;

    }

    public static enum PlotType {
        SCATTER,
        SCATTER_WITH_TREND,
        LINE,
        LINE_AND_POINTS,
        AREA,
        BAR,
        GRID,
        GRID_WITH_TREND;

    }

    public static enum JoinType {
        INNER,
        OUTER,
        LEFT,
        RIGHT;

    }

    public static enum SortDirection {
        ASCENDING,
        DESCENDING;

    }

    public static enum Axis {
        ROWS,
        COLUMNS;

    }
}

