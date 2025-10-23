/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public class IteratorUtils {
    private IteratorUtils() {
    }

    public static <T> List<T> toList(Iterator<T> iterator) {
        if (null == iterator) {
            return Collections.emptyList();
        }
        ArrayList<T> list = new ArrayList<T>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    public static <T> Iterator<T> emptyIterator() {
        return Collections.emptyIterator();
    }

    public static <T> Iterator<T> singleIterator(T item) {
        return new SingleIterator<T>(item);
    }

    public static <T> Iterator<T> toIterator(T ... items) {
        if (null == items) {
            return IteratorUtils.emptyIterator();
        }
        return Arrays.stream(items).iterator();
    }

    public static <V> Iterator<V> toIterator(Collection<V> collection) {
        if (null == collection) {
            return IteratorUtils.emptyIterator();
        }
        return collection.iterator();
    }

    public static <K, V> Iterator<V> toIterator(Map<K, V> map) {
        if (null == map) {
            return IteratorUtils.emptyIterator();
        }
        return map.values().iterator();
    }

    public static final <S, T> Iterator<T> toIterator(final Iterator<S> iterator, final Function<S, T> function) {
        return new Iterator<T>(){

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public void remove() {
                iterator.remove();
            }

            @Override
            public T next() {
                return function.apply(iterator.next());
            }
        };
    }

    public static final <S, T> Iterator<T> flatIterator(final Iterator<S> iterator, final Function<S, Iterator<T>> function) {
        return new Iterator<T>(){
            private Iterator<T> currentIterator = Collections.emptyIterator();

            @Override
            public boolean hasNext() {
                if (this.currentIterator.hasNext()) {
                    return true;
                }
                while (iterator.hasNext()) {
                    this.currentIterator = (Iterator)function.apply(iterator.next());
                    if (!this.currentIterator.hasNext()) continue;
                    return true;
                }
                return false;
            }

            @Override
            public void remove() {
                iterator.remove();
            }

            @Override
            public T next() {
                if (this.hasNext()) {
                    return this.currentIterator.next();
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static final <S> Iterator<S> filterIterator(final Iterator<S> iterator, final Predicate<S> predicate) {
        return new Iterator<S>(){
            S nextResult = null;

            @Override
            public boolean hasNext() {
                if (null != this.nextResult) {
                    return true;
                }
                this.advance();
                return null != this.nextResult;
            }

            @Override
            public void remove() {
                iterator.remove();
            }

            @Override
            public S next() {
                try {
                    if (null != this.nextResult) {
                        Object s = this.nextResult;
                        return s;
                    }
                    this.advance();
                    if (null != this.nextResult) {
                        Object s = this.nextResult;
                        return s;
                    }
                    throw new NoSuchElementException();
                }
                finally {
                    this.nextResult = null;
                }
            }

            private final void advance() {
                this.nextResult = null;
                while (iterator.hasNext()) {
                    Object s = iterator.next();
                    if (!predicate.test(s)) continue;
                    this.nextResult = s;
                    return;
                }
            }
        };
    }

    public static class ArrayIterator<T>
    implements Iterator<T> {
        private final T[] elems;
        private int pos;

        public ArrayIterator(T[] elems) {
            this.elems = elems;
            this.pos = 0;
        }

        @Override
        public T next() {
            return this.elems[this.pos++];
        }

        @Override
        public boolean hasNext() {
            return this.pos < this.elems.length;
        }
    }

    public static class SingleIterator<T>
    implements Iterator<T> {
        private T element;

        public SingleIterator(T element) {
            this.element = element;
        }

        @Override
        public boolean hasNext() {
            return this.element != null;
        }

        @Override
        public T next() {
            T ret = this.element;
            this.element = null;
            return ret;
        }
    }
}

