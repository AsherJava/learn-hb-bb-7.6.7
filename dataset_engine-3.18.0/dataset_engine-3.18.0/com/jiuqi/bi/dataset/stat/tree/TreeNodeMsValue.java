/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.bi.dataset.stat.tree;

import com.jiuqi.bi.dataset.stat.tree.StatDataRecord;
import com.jiuqi.bi.dataset.stat.tree.StatTreeNode;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import java.math.BigDecimal;
import java.util.List;

abstract class TreeNodeMsValue
implements Cloneable {
    private Object computedVal;
    private Object dsRealValue;

    TreeNodeMsValue() {
    }

    public final Object getValue(StatTreeNode bindingNode, StatDataRecord bindingRecord, int msPos, int unitCodeColumnIndex) {
        if (this.computedVal != null) {
            return this.computedVal;
        }
        this.computedVal = bindingNode.isLeaf() ? this.dsRealValue : this.doCompute(bindingNode, bindingRecord, msPos, unitCodeColumnIndex);
        return this.computedVal;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("computedVal->").append(this.computedVal).append("\t");
        b.append("realVal->").append(this.dsRealValue);
        return b.toString();
    }

    private Object doCompute(StatTreeNode currNode, StatDataRecord currRecord, int msPos, int skipColumnIndex) {
        TreeNodeMsValue msValue = currRecord.getMsValue(msPos);
        List<StatTreeNode> children = currNode.getChildren();
        for (StatTreeNode child : children) {
            List<StatDataRecord> childRecords = child.getDataRecords();
            for (StatDataRecord record : childRecords) {
                TreeNodeMsValue value;
                Object v;
                if (!this.match(record, currRecord, skipColumnIndex) || (v = (value = record.getMsValue(msPos)).getValue(child, record, msPos, skipColumnIndex)) == null) continue;
                this.addValue(v);
            }
        }
        return msValue.toValue();
    }

    private boolean match(StatDataRecord r1, StatDataRecord r2, int skipColumnIndex) {
        int len = r1.getDimColSize();
        for (int i = 0; i < len; ++i) {
            Object v2;
            Object v1;
            int v;
            if (i == skipColumnIndex || (v = DataType.compareObject((Object)(v1 = r1.getDimValue(i)), (Object)(v2 = r2.getDimValue(i)))) == 0) continue;
            return false;
        }
        return true;
    }

    public void setDataRowValue(Object value) {
        this.dsRealValue = value;
    }

    protected abstract void addValue(Object var1);

    protected abstract Object toValue();

    public TreeNodeMsValue clone() {
        try {
            return (TreeNodeMsValue)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    static class CountNodeValue
    extends TreeNodeMsValue {
        private Integer count;

        CountNodeValue() {
        }

        protected void statAdd(StatTreeNode node, TreeNodeMsValue value) {
            CountNodeValue dest = (CountNodeValue)value;
            Integer v = node.isLeaf() ? 1 : dest.count;
            if (v != null) {
                Integer val = v;
                this.count = this.count == null ? val : Integer.valueOf(this.count + val);
            }
        }

        @Override
        protected void addValue(Object value) {
            this.count = this.count == null ? Integer.valueOf(1) : Integer.valueOf(this.count + 1);
        }

        @Override
        protected Object toValue() {
            return this.count;
        }
    }

    static class BigDecimalAvgNodeValue
    extends TreeNodeMsValue {
        private BigDecimal sum;
        private int count;

        BigDecimalAvgNodeValue() {
        }

        @Override
        protected void addValue(Object value) {
            BigDecimal val = (BigDecimal)value;
            this.sum = this.sum == null ? val : this.sum.add(val);
        }

        @Override
        protected Object toValue() {
            return this.sum == null ? null : this.sum.divide(BigDecimal.valueOf(this.count));
        }
    }

    static class DoubleAvgNodeValue
    extends TreeNodeMsValue {
        private Double sum;
        private int count;

        DoubleAvgNodeValue() {
        }

        @Override
        protected void addValue(Object value) {
            Double val = (Double)value;
            this.sum = this.sum == null ? val : Double.valueOf(this.sum + val);
        }

        @Override
        protected Object toValue() {
            return this.sum == null ? null : Double.valueOf(this.sum / (double)this.count);
        }
    }

    static class IntAvgNodeValue
    extends TreeNodeMsValue {
        private Integer sum;
        private int count;

        IntAvgNodeValue() {
        }

        @Override
        protected void addValue(Object value) {
            Integer val = (Integer)value;
            this.sum = this.sum == null ? val : Integer.valueOf(this.sum + val);
        }

        @Override
        protected Object toValue() {
            return this.sum == null ? null : Integer.valueOf(this.sum / this.count);
        }
    }

    static class MinNodeValue
    extends TreeNodeMsValue {
        private int dataType;
        private Object min;

        public MinNodeValue(int dataType) {
            this.dataType = dataType;
        }

        @Override
        protected void addValue(Object value) {
            if (this.min == null) {
                this.min = value;
                return;
            }
            try {
                int rs = DataType.compare((int)this.dataType, (Object)this.min, (Object)value);
                if (rs > 0) {
                    this.min = value;
                }
            }
            catch (SyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected Object toValue() {
            return this.min;
        }
    }

    static class MaxNodeValue
    extends TreeNodeMsValue {
        private int dataType;
        private Object max;

        public MaxNodeValue(int dataType) {
            this.dataType = dataType;
        }

        @Override
        protected void addValue(Object value) {
            if (this.max == null) {
                this.max = value;
                return;
            }
            try {
                int rs = DataType.compare((int)this.dataType, (Object)this.max, (Object)value);
                if (rs < 0) {
                    this.max = value;
                }
            }
            catch (SyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected Object toValue() {
            return this.max;
        }
    }

    static class DoubleSumNodeValue
    extends TreeNodeMsValue {
        private Double sum;

        DoubleSumNodeValue() {
        }

        @Override
        protected void addValue(Object value) {
            Double val = (Double)value;
            this.sum = this.sum == null ? val : Double.valueOf(this.sum + val);
        }

        @Override
        protected Object toValue() {
            return this.sum;
        }
    }

    class BigDecimalSumNodeValue
    extends TreeNodeMsValue {
        private BigDecimal sum;

        BigDecimalSumNodeValue() {
        }

        @Override
        protected void addValue(Object value) {
            BigDecimal val = (BigDecimal)value;
            this.sum = this.sum == null ? val : this.sum.add(val);
        }

        @Override
        protected Object toValue() {
            return this.sum;
        }
    }

    static class IntSumNodeValue
    extends TreeNodeMsValue {
        private Integer sum;

        IntSumNodeValue() {
        }

        @Override
        protected void addValue(Object value) {
            Integer val = (Integer)value;
            this.sum = this.sum == null ? val : Integer.valueOf(this.sum + val);
        }

        @Override
        protected Object toValue() {
            return this.sum;
        }
    }
}

