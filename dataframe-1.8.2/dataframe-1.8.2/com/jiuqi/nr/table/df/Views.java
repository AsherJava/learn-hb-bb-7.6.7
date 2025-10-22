/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import com.jiuqi.nr.table.df.DataFrame;
import java.util.AbstractList;
import java.util.List;
import java.util.function.Function;

public class Views {

    public static class TransformedSeriesView<V, U>
    extends AbstractList<U> {
        protected final DataFrame<V> df;
        protected final int index;
        protected final boolean transpose;
        protected final Function<V, U> transform;

        public TransformedSeriesView(DataFrame<V> df, Function<V, U> transform, int index, boolean transpose) {
            this.df = df;
            this.transform = transform;
            this.index = index;
            this.transpose = transpose;
        }

        @Override
        public U get(int index) {
            V value = this.transpose ? this.df.get(index, this.index) : this.df.get(this.index, index);
            return this.transform.apply(value);
        }

        @Override
        public int size() {
            return this.transpose ? this.df.length() : this.df.size();
        }
    }

    public static class TransformedView<V, U>
    extends AbstractList<List<U>> {
        protected final DataFrame<V> df;
        protected final Function<V, U> transform;
        protected final boolean transpose;

        public TransformedView(DataFrame<V> df, Function<V, U> transform, boolean transpose) {
            this.df = df;
            this.transform = transform;
            this.transpose = transpose;
        }

        @Override
        public List<U> get(int index) {
            return new TransformedSeriesView<V, U>(this.df, this.transform, index, !this.transpose);
        }

        @Override
        public int size() {
            return this.transpose ? this.df.length() : this.df.size();
        }
    }

    public static class FillNaFunction<V>
    implements Function<V, V> {
        private final V fill;

        public FillNaFunction(V fill) {
            this.fill = fill;
        }

        @Override
        public V apply(V value) {
            return value == null ? this.fill : value;
        }
    }

    public static class FlatView<E>
    extends AbstractList<E> {
        private final DataFrame<E> df;

        public FlatView(DataFrame<E> df) {
            this.df = df;
        }

        @Override
        public E get(int index) {
            return this.df.get(index % this.df.length(), index / this.df.length());
        }

        @Override
        public int size() {
            return this.df.size() * this.df.length();
        }
    }

    public static class SeriesListView<E>
    extends AbstractList<E> {
        private final DataFrame<E> df;
        private final int index;
        private final boolean transpose;

        public SeriesListView(DataFrame<E> df, int index, boolean transpose) {
            this.df = df;
            this.index = index;
            this.transpose = transpose;
        }

        @Override
        public E get(int index) {
            return this.transpose ? this.df.get(this.index, index) : this.df.get(index, this.index);
        }

        @Override
        public int size() {
            return this.transpose ? this.df.size() : this.df.length();
        }
    }

    public static class ListView<E>
    extends AbstractList<List<E>> {
        private final DataFrame<E> df;
        private final boolean transpose;

        public ListView(DataFrame<E> df, boolean transpose) {
            this.df = df;
            this.transpose = transpose;
        }

        @Override
        public int size() {
            return this.transpose ? this.df.length() : this.df.size();
        }

        @Override
        public List<E> get(int row) {
            return new SeriesListView<E>(this.df, row, this.transpose);
        }
    }
}

