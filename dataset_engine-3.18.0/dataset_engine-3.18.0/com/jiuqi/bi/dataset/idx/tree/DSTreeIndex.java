/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.idx.tree;

import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.expression.DSExpression;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFormularManager;
import com.jiuqi.bi.dataset.expression.DatasetFormulaParser;
import com.jiuqi.bi.dataset.idx.DSIndexException;
import com.jiuqi.bi.dataset.idx.tree.IndexKey;
import com.jiuqi.bi.dataset.idx.tree.TreeIndexItem;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DSTreeIndex {
    private BIDataSetImpl dataset;
    private IndexKey key;
    private List<TreeIndexItem> indexItems = new ArrayList<TreeIndexItem>();
    private int[] columnIndeies;
    private boolean hasBuild = false;
    private static final Object nullObject = new Object();

    public DSTreeIndex(BIDataSetImpl dataset, String[] columns) {
        this.dataset = dataset;
        this.key = new IndexKey(columns);
        this.columnIndeies = new int[columns.length];
        for (int i = 0; i < columns.length; ++i) {
            this.columnIndeies[i] = dataset.getMetadata().indexOf(columns[i]);
        }
    }

    public IndexKey getKey() {
        return this.key;
    }

    public synchronized void build() {
        if (this.hasBuild) {
            return;
        }
        List<Integer> records = this.copyDataset();
        this.sortRecord(records);
        this.buildTreeIndex(records);
        this.hasBuild = true;
    }

    public void print() {
        System.out.println("\u5f00\u59cb\u8f93\u51fa\u7d22\u5f15\u8bb0\u5f55\u9879\uff1a");
        this.printIndexItem(this.indexItems);
    }

    private void printIndexItem(List<TreeIndexItem> idxItems) {
        for (TreeIndexItem item : idxItems) {
            System.out.println();
            if (item.isLeaf()) {
                List<Integer> items = item.getRecordIndexes();
                for (Integer rowIdx : items) {
                    Object[] value = this.dataset.getRowData(rowIdx);
                    System.out.println("--" + Arrays.toString(value));
                }
                continue;
            }
            System.out.print(item.getRowIndex());
            this.printIndexItem(item.getDirectSubIndexItems());
        }
    }

    public int[] search(Object ... data) throws DSIndexException {
        if (!this.hasBuild) {
            throw new DSIndexException("\u7d22\u5f15\u4fe1\u606f\u672a\u6784\u5efa\uff0c\u65e0\u6cd5\u8fdb\u884c\u67e5\u627e");
        }
        if (data.length > this.columnIndeies.length) {
            throw new DSIndexException("\u7d22\u5f15\u68c0\u7d22\u5f02\u5e38\uff0c\u4f20\u5165\u7684\u952e\u503c\u4e0e\u7d22\u5f15\u5217\u4e0d\u4e00\u81f4");
        }
        List<TreeIndexItem> idies = this.indexItems;
        int index = -1;
        for (int i = 0; i < data.length; ++i) {
            index = this.binarySearchIndex(idies, this.columnIndeies[i], data[i]);
            if (index == -1) {
                return new int[0];
            }
            if (i == this.columnIndeies.length - 1) continue;
            idies = idies.get(index).getDirectSubIndexItems();
        }
        if (index == -1) {
            return new int[0];
        }
        List<Integer> items = idies.get(index).getRecordIndexes();
        int[] rs = new int[items.size()];
        for (int i = 0; i < items.size(); ++i) {
            rs[i] = items.get(i);
        }
        return rs;
    }

    public int[] filter(List<FilterItem> filterItems) throws DSIndexException, SyntaxException {
        List<TreeIndexItem> tItems = this.indexItems;
        ArrayList<TreeIndexItem> nItems = new ArrayList<TreeIndexItem>();
        int curLv = 0;
        for (FilterItem filterItem : filterItems) {
            String fieldName = filterItem.getFieldName();
            int fieldIdx = this.dataset.getMetadata().indexOf(fieldName);
            if (fieldIdx != this.columnIndeies[curLv]) {
                throw new DSIndexException("\u4f20\u5165\u7684\u8fc7\u6ee4\u5217\u8868\u4e2d\uff0c\u5bf9\u5e94\u7684\u5b57\u6bb5\u4fe1\u606f\u4e0e\u7d22\u5f15\u4e2d\u7684\u5b57\u6bb5\u4e0d\u4e00\u81f4");
            }
            String expr = filterItem.getExpr();
            List<Object> keys = filterItem.getKeyList();
            nItems.clear();
            for (TreeIndexItem ti : tItems) {
                if (StringUtils.isNotEmpty((String)expr) && this.judge(ti, fieldIdx, expr)) {
                    nItems.add(ti);
                    continue;
                }
                if (keys == null || !this.judge(ti, fieldIdx, keys)) continue;
                nItems.add(ti);
            }
            tItems = this.getDirectSubTreeIndexItems(tItems);
            ++curLv;
        }
        ArrayList<Integer> trs = new ArrayList<Integer>();
        for (TreeIndexItem item : nItems) {
            trs.addAll(item.getRecordIndexes());
        }
        int[] nArray = new int[trs.size()];
        for (int i = 0; i < trs.size(); ++i) {
            nArray[i] = (Integer)trs.get(i);
        }
        return nArray;
    }

    public int getItemCount(int level) {
        List<TreeIndexItem> tIdxes = this.getLevelIndexItems(level);
        return tIdxes.size();
    }

    public int[] getLevelRowIndexes(int level) {
        List<TreeIndexItem> tIdxes = this.getLevelIndexItems(level);
        int[] rowIdxes = new int[tIdxes.size()];
        for (int i = 0; i < tIdxes.size(); ++i) {
            rowIdxes[i] = tIdxes.get(i).getRowIndex();
        }
        return rowIdxes;
    }

    public List<int[]> getLevelRowIndexesByGroup(int level) {
        List<TreeIndexItem> tIdxes = this.getLevelIndexItems(level);
        ArrayList<int[]> rs = new ArrayList<int[]>(tIdxes.size());
        for (TreeIndexItem idx : tIdxes) {
            List<Integer> records = idx.getRecordIndexes();
            int[] rIdxes = new int[records.size()];
            for (int i = 0; i < records.size(); ++i) {
                rIdxes[i] = records.get(i);
            }
            rs.add(rIdxes);
        }
        return rs;
    }

    private boolean judge(TreeIndexItem item, int fieldIdx, String expr) throws SyntaxException {
        DatasetFormulaParser parser = DSFormularManager.getInstance().createParser(this.dataset);
        DSFormulaContext context = new DSFormulaContext(this.dataset, this.dataset.get(item.getRowIndex()));
        DSExpression dsExpr = parser.parseCond(expr, context);
        return dsExpr.judge(context);
    }

    private boolean judge(TreeIndexItem item, int fieldIdx, List<Object> keys) {
        int rowIdx = item.getRowIndex();
        Object data = this.dataset.getRowData(rowIdx)[fieldIdx];
        if (data == null) {
            return false;
        }
        for (Object key : keys) {
            if (!data.equals(key)) continue;
            return true;
        }
        return false;
    }

    private List<TreeIndexItem> getDirectSubTreeIndexItems(List<TreeIndexItem> currItems) {
        ArrayList<TreeIndexItem> items = new ArrayList<TreeIndexItem>();
        for (TreeIndexItem item : currItems) {
            items.addAll(item.getDirectSubIndexItems());
        }
        return items;
    }

    private List<TreeIndexItem> getLevelIndexItems(int level) {
        List<TreeIndexItem> tIdxes = this.indexItems;
        for (int i = 0; i < level; ++i) {
            ArrayList<TreeIndexItem> nIdxes = new ArrayList<TreeIndexItem>();
            for (TreeIndexItem item : tIdxes) {
                nIdxes.addAll(item.getDirectSubIndexItems());
            }
            tIdxes = nIdxes;
        }
        return tIdxes;
    }

    private int binarySearchIndex(List<TreeIndexItem> idies, int colIndex, Object data) {
        int l = 0;
        int r = idies.size() - 1;
        while (l <= r) {
            int half = (r - l) / 2 + l;
            int idx = idies.get(half).getRowIndex();
            Object[] dataRow = this.dataset.getRowData(idx);
            int result = DSTreeIndex.compare(data, dataRow[colIndex]);
            if (l == r && result != 0) {
                return -1;
            }
            if (result > 0) {
                l = half + 1;
                continue;
            }
            if (result < 0) {
                r = half - 1;
                continue;
            }
            return half;
        }
        return -1;
    }

    private List<Integer> copyDataset() {
        int count = this.dataset.getRecordCount();
        ArrayList<Integer> records = new ArrayList<Integer>(count);
        for (int i = 0; i < count; ++i) {
            records.add(i);
        }
        return records;
    }

    private void sortRecord(List<Integer> records) {
        Collections.sort(records, new Comparator<Integer>(){

            @Override
            public int compare(Integer o1, Integer o2) {
                for (int columnIndex : DSTreeIndex.this.columnIndeies) {
                    Object obj1 = DSTreeIndex.this.dataset.get(o1).getValue(columnIndex);
                    Object obj2 = DSTreeIndex.this.dataset.get(o2).getValue(columnIndex);
                    if (obj1 == null) {
                        return -1;
                    }
                    if (obj2 == null) {
                        return 1;
                    }
                    int result = ((Comparable)obj1).compareTo(obj2);
                    if (result == 0) continue;
                    return result;
                }
                return 0;
            }
        });
    }

    private void buildTreeIndex(List<Integer> records) {
        BufferIdx[] bufferData = new BufferIdx[this.columnIndeies.length];
        for (Integer record : records) {
            for (int i = 0; i < this.columnIndeies.length; ++i) {
                Object data = this.dataset.get(record).getValue(this.columnIndeies[i]);
                if (data == null) {
                    data = nullObject;
                }
                if (bufferData[i] == null || !bufferData[i].data.equals(data)) {
                    TreeIndexItem idxItem;
                    if (i == 0) {
                        idxItem = new TreeIndexItem(record);
                        this.indexItems.add(idxItem);
                    } else {
                        idxItem = bufferData[i - 1].idx.addSubIndex(record);
                    }
                    if (i == this.columnIndeies.length - 1) {
                        idxItem.initRecordIndexList();
                        idxItem.addRecordIndex(record);
                    }
                    bufferData[i] = new BufferIdx(idxItem, data);
                    for (int t = i + 1; t < bufferData.length; ++t) {
                        bufferData[t] = null;
                    }
                    continue;
                }
                if (i != this.columnIndeies.length - 1) continue;
                bufferData[i].idx.addRecordIndex(record);
            }
        }
        for (int i = 0; i < bufferData.length; ++i) {
            bufferData[i] = null;
        }
    }

    private static int compare(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        return ((Comparable)o1).compareTo(o2);
    }

    private final class BufferIdx {
        public TreeIndexItem idx;
        public Object data;

        public BufferIdx(TreeIndexItem idx, Object data) {
            this.idx = idx;
            this.data = data;
        }
    }
}

