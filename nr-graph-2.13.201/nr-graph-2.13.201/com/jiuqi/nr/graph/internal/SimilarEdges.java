/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IEdge;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.ILabelabled;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SimilarEdges
extends HashSet<IEdge>
implements ILabelabled<EdgeLabel> {
    private static final long serialVersionUID = 2052596660580631534L;
    private static final SimilarEdges EMPTY_ADJACENT_NODES = new SimilarEdges(null){
        private static final long serialVersionUID = -8913011339610655602L;

        @Override
        public Iterator<IEdge> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean contains(Object obj) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return c.isEmpty();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            if (a.length > 0) {
                a[0] = null;
            }
            return a;
        }

        @Override
        public void forEach(Consumer<? super IEdge> action) {
            Objects.requireNonNull(action);
        }

        @Override
        public boolean removeIf(Predicate<? super IEdge> filter) {
            Objects.requireNonNull(filter);
            return false;
        }

        @Override
        public Spliterator<IEdge> spliterator() {
            return Spliterators.emptySpliterator();
        }
    };
    private final EdgeLabel label;

    public SimilarEdges(EdgeLabel label) {
        this.label = label;
    }

    @Override
    public EdgeLabel getLabel() {
        return this.label;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + (this.label == null ? 0 : this.label.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SimilarEdges other = (SimilarEdges)obj;
        return !(this.label == null ? other.label != null : !this.label.equals(other.label));
    }

    public static SimilarEdges emptyAdjacentNodes() {
        return EMPTY_ADJACENT_NODES;
    }
}

