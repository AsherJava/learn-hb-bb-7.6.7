/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.label.AbstractLabel;
import com.jiuqi.nr.graph.label.ILabelabled;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SimilarNodes
extends HashMap<String, INode>
implements ILabelabled<AbstractLabel> {
    private static final long serialVersionUID = -3733584112053130270L;
    private static final SimilarNodes EMPTY_SIMILAR_NODES = new SimilarNodes(null){
        private static final long serialVersionUID = -3819124953260439344L;

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public INode get(Object key) {
            return null;
        }

        @Override
        public Set<String> keySet() {
            return Collections.emptySet();
        }

        @Override
        public Collection<INode> values() {
            return Collections.emptySet();
        }

        @Override
        public Set<Map.Entry<String, INode>> entrySet() {
            return Collections.emptySet();
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public INode getOrDefault(Object key, INode defaultValue) {
            return defaultValue;
        }

        @Override
        public void forEach(BiConsumer<? super String, ? super INode> action) {
            Objects.requireNonNull(action);
        }

        @Override
        public void replaceAll(BiFunction<? super String, ? super INode, ? extends INode> function) {
            Objects.requireNonNull(function);
        }

        @Override
        public INode putIfAbsent(String key, INode value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object key, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(String key, INode oldValue, INode newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public INode replace(String key, INode value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public INode computeIfAbsent(String key, Function<? super String, ? extends INode> mappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public INode computeIfPresent(String key, BiFunction<? super String, ? super INode, ? extends INode> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public INode compute(String key, BiFunction<? super String, ? super INode, ? extends INode> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public INode merge(String key, INode value, BiFunction<? super INode, ? super INode, ? extends INode> remappingFunction) {
            throw new UnsupportedOperationException();
        }
    };
    private final AbstractLabel label;

    public SimilarNodes(AbstractLabel label) {
        this.label = label;
    }

    public SimilarNodes(AbstractLabel label, int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.label = label;
    }

    @Override
    public AbstractLabel getLabel() {
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
        SimilarNodes other = (SimilarNodes)obj;
        return !(this.label == null ? other.label != null : !this.label.equals(other.label));
    }

    public static SimilarNodes emptySimilarNodes() {
        return EMPTY_SIMILAR_NODES;
    }
}

