/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.trie.AhoCorasickDoubleArrayTrie
 */
package com.jiuqi.bi.dataset.idx;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.idx.DSColumnIndex;
import com.jiuqi.bi.util.trie.AhoCorasickDoubleArrayTrie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DSTrieColumnIndex
extends DSColumnIndex {
    private AhoCorasickDoubleArrayTrie<List<Integer>> trie = new AhoCorasickDoubleArrayTrie();
    private List<Integer> nullValueList = new ArrayList<Integer>();
    Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

    public static void main(String[] args) throws DataSetException {
        Metadata metadata = new Metadata();
        metadata.addColumn(new Column("name", 6));
        metadata.addColumn(new Column("value", 3));
        MemoryDataSet memory = new MemoryDataSet(BIDataSetFieldInfo.class, metadata);
        memory.add(new Object[]{"\u4e45\u5176\u8f6f\u4ef6", 100});
        memory.add(new Object[]{"\u534e\u590f\u7535\u901a", 30});
        memory.add(new Object[]{"\u4e45\u5176\u8f6f\u4ef6", 50});
        memory.add(new Object[]{"\u5c0f\u8681\u79d1\u6280", 80});
        BIDataSetImpl ds = new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)memory);
        DSTrieColumnIndex idx = new DSTrieColumnIndex(ds, 0);
        idx.build();
        List<Integer> pos = idx.search("\u4e45\u5176\u8f6f\u4ef6");
        System.out.println(pos);
    }

    public DSTrieColumnIndex(BIDataSetImpl dataset, int colIdx) {
        super(dataset, colIdx);
    }

    @Override
    public List<Integer> search(Object data) {
        if (data == null) {
            return new ArrayList<Integer>(this.nullValueList);
        }
        String txt = (String)data;
        List v = (List)this.trie.get(txt);
        return v == null ? new ArrayList() : v;
    }

    @Override
    public synchronized void build() {
        if (this.hasBuild) {
            return;
        }
        int count = this.dataset.getRecordCount();
        HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
        for (int i = 0; i < count; ++i) {
            String word = (String)this.dataset.getRowData(i)[this.colIdx];
            if (word == null) {
                this.nullValueList.add(i);
                continue;
            }
            ArrayList<Integer> v = (ArrayList<Integer>)map.get(word);
            if (v == null) {
                v = new ArrayList<Integer>();
                map.put(word, v);
            }
            v.add(i);
        }
        this.trie.build(map);
        this.commit();
    }

    @Override
    protected void process(int rowIdx, Object[] rowData) {
        String word = (String)rowData[this.colIdx];
        if (word == null) {
            this.nullValueList.add(rowIdx);
        } else {
            List<Integer> v = this.map.get(word);
            if (v == null) {
                v = new ArrayList<Integer>();
                this.map.put(word, v);
            }
            v.add(rowIdx);
        }
    }

    @Override
    protected void commit() {
        if (this.map != null && !this.map.isEmpty()) {
            this.trie.build(this.map);
            this.map = null;
        }
        this.hasBuild = true;
    }
}

