/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.nested;

import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.impl.nested.LinkNode;
import com.jiuqi.nr.datacrud.impl.nested.NestedRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NestedSortTable {
    private Map<String, NestedRow> rootMap;
    private LinkNode head;
    private final List<LinkSort> sorts;

    public NestedSortTable(List<String> links, List<LinkSort> sorts) {
        if (links == null || links.isEmpty()) {
            throw new UnsupportedOperationException("Unsupported nested floating parameters");
        }
        this.sorts = sorts;
        LinkNode pre = null;
        for (String link : links) {
            LinkNode linkNode = new LinkNode(link);
            if (pre == null) {
                this.head = pre = linkNode;
                continue;
            }
            pre.setNext(linkNode);
            pre = linkNode;
        }
    }

    public void addRow(IRowData row) {
        if (this.rootMap == null) {
            this.rootMap = new HashMap<String, NestedRow>();
        }
        String link = this.head.getValue();
        IDataValue dataValue = row.getDataValueByLink(link);
        String value = dataValue.toString();
        NestedRow groupRow = this.rootMap.computeIfAbsent(value, k -> new NestedRow(this.head, this.sorts));
        groupRow.addRow(row);
    }

    public List<IRowData> sort() {
        if (this.rootMap == null) {
            return Collections.emptyList();
        }
        ArrayList<IRowData> list = new ArrayList<IRowData>();
        ArrayList<NestedRow> rows = new ArrayList<NestedRow>(this.rootMap.values());
        this.traversalHelper(rows, list);
        return list;
    }

    private void traversalHelper(List<NestedRow> nodes, List<IRowData> result) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        nodes.sort(null);
        for (NestedRow node : nodes) {
            List<IRowData> rows = node.getRows();
            if (rows != null && !rows.isEmpty()) {
                result.addAll(rows);
            }
            this.traversalHelper(node.getChildren(), result);
        }
    }
}

