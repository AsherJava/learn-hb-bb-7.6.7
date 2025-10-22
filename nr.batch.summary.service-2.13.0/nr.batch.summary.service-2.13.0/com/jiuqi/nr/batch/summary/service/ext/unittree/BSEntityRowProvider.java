/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BSEntityRowProvider
implements IUnitTreeEntityRowProvider {
    private Map<String, String> nodeTypeMap = new HashMap<String, String>();
    private Map<String, IUnitTreeEntityRowProvider> providerMap;

    public BSEntityRowProvider(Map<String, IUnitTreeEntityRowProvider> providerMap) {
        this.providerMap = providerMap;
    }

    public List<IEntityRow> getAllRows() {
        ArrayList<IEntityRow> allRows = new ArrayList<IEntityRow>();
        for (Map.Entry<String, IUnitTreeEntityRowProvider> entry : this.providerMap.entrySet()) {
            String schemeKey = entry.getKey();
            IUnitTreeEntityRowProvider provider = entry.getValue();
            provider.getAllRows().forEach(row -> {
                this.nodeTypeMap.put(row.getEntityKeyData(), schemeKey);
                allRows.add((IEntityRow)row);
            });
        }
        return allRows;
    }

    public List<IEntityRow> getRootRows() {
        ArrayList<IEntityRow> rootRows = new ArrayList<IEntityRow>();
        for (Map.Entry<String, IUnitTreeEntityRowProvider> entry : this.providerMap.entrySet()) {
            String schemeKey = entry.getKey();
            IUnitTreeEntityRowProvider provider = entry.getValue();
            provider.getRootRows().forEach(row -> {
                this.nodeTypeMap.put(row.getEntityKeyData(), schemeKey);
                rootRows.add((IEntityRow)row);
            });
        }
        return rootRows;
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        IUnitTreeEntityRowProvider provider = this.providerMap.get(this.nodeTypeMap.get(parent.getKey()));
        return provider.getChildRows(parent);
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        IUnitTreeEntityRowProvider provider = this.providerMap.get(this.nodeTypeMap.get(parent.getKey()));
        return provider.getDirectChildCount(parent);
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        IUnitTreeEntityRowProvider provider = this.providerMap.get(this.nodeTypeMap.get(rowData.getKey()));
        if (provider != null) {
            return provider.getNodePath(rowData);
        }
        return new String[0];
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        for (Map.Entry<String, IUnitTreeEntityRowProvider> entry : this.providerMap.entrySet()) {
            IUnitTreeEntityRowProvider provider = entry.getValue();
            IEntityRow row = provider.findEntityRow(rowData);
            if (row == null) continue;
            this.nodeTypeMap.put(row.getEntityKeyData(), entry.getKey());
            return row;
        }
        return null;
    }
}

