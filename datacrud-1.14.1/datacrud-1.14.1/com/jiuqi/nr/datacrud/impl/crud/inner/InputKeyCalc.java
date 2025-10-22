/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datacrud.impl.crud.inner.InputRow;
import com.jiuqi.nr.datacrud.impl.crud.inner.KeyNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class InputKeyCalc
implements Iterable<KeyNode> {
    private final Map<DimensionValueSet, KeyNodeImpl> keyNodeMap = new HashMap<DimensionValueSet, KeyNodeImpl>();
    private boolean existNodeType;

    @Override
    @NotNull
    public Iterator<KeyNode> iterator() {
        return new KeyNodeIterator(this.keyNodeMap.keySet().iterator());
    }

    public KeyNode getKeyNode(DimensionValueSet dbRowKeys) {
        return this.keyNodeMap.get(dbRowKeys);
    }

    public void rowCountCalc(InputRow row) {
        DimensionValueSet rowKey = row.getRowKeys();
        KeyNodeImpl keyNode = this.keyNodeMap.get(rowKey);
        if (keyNode == null) {
            keyNode = new KeyNodeImpl();
            keyNode.key = rowKey;
            keyNode.count = 0;
            keyNode.index = new ArrayList();
            this.keyNodeMap.put(rowKey, keyNode);
        }
        keyNode.index.add(row.getRowIndex());
        switch (row.getType()) {
            case 3: {
                keyNode.count--;
                break;
            }
            case 1: {
                keyNode.count++;
                break;
            }
            case 2: 
            case 4: {
                DimensionValueSet newKey = row.getNewKey();
                if (newKey == null) break;
                KeyNodeImpl newKeyNode = this.keyNodeMap.get(newKey);
                if (newKeyNode == null) {
                    newKeyNode = new KeyNodeImpl();
                    newKeyNode.key = newKey;
                    newKeyNode.count = 0;
                    newKeyNode.index = new ArrayList();
                    this.keyNodeMap.put(newKey, newKeyNode);
                }
                newKeyNode.index.add(row.getRowIndex());
                keyNode.count--;
                newKeyNode.count++;
                break;
            }
            case 0: {
                this.existNodeType = true;
                keyNode.count++;
                break;
            }
        }
    }

    public void removeKey(DimensionValueSet rowKey) {
        this.keyNodeMap.remove(rowKey);
    }

    public boolean isExistNodeType() {
        return this.existNodeType;
    }

    public void setExistNodeType(boolean existNodeType) {
        this.existNodeType = existNodeType;
    }

    public class KeyNodeIterator
    implements Iterator<KeyNode> {
        private final Iterator<DimensionValueSet> keyIterator;

        public KeyNodeIterator(Iterator<DimensionValueSet> keyIterator) {
            this.keyIterator = keyIterator;
        }

        @Override
        public boolean hasNext() {
            return this.keyIterator.hasNext();
        }

        @Override
        public KeyNode next() {
            DimensionValueSet next = this.keyIterator.next();
            return (KeyNode)InputKeyCalc.this.keyNodeMap.get(next);
        }
    }

    public static class KeyNodeImpl
    implements KeyNode {
        private DimensionValueSet key;
        private int count;
        private List<Integer> index;

        @Override
        public DimensionValueSet getKey() {
            return this.key;
        }

        @Override
        public int getCount() {
            return this.count;
        }

        @Override
        public List<Integer> getIndexes() {
            return this.index;
        }
    }
}

