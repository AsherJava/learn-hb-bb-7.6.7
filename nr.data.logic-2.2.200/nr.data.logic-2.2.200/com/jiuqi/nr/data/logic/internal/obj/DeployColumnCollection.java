/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class DeployColumnCollection {
    private final Map<String, DesignColumnModelDefine> allOldColumnsMap;
    private final List<DesignColumnModelDefine> createColumnList = new ArrayList<DesignColumnModelDefine>();
    private final List<DesignColumnModelDefine> modifyColumnList = new ArrayList<DesignColumnModelDefine>();
    private final List<DesignColumnModelDefine> deleteColumnList = new ArrayList<DesignColumnModelDefine>();
    private final Map<String, DesignColumnModelDefine> allColumnsMap = new HashMap<String, DesignColumnModelDefine>();

    public DeployColumnCollection(Map<String, DesignColumnModelDefine> allDesColumnsMap) {
        this.allOldColumnsMap = CollectionUtils.isEmpty(allDesColumnsMap) ? new HashMap<String, DesignColumnModelDefine>() : allDesColumnsMap;
    }

    public void appendCreCol(String colCode, DesignColumnModelDefine column) {
        this.createColumnList.add(column);
        this.allColumnsMap.put(colCode, column);
    }

    public void appendModCol(String colCode, DesignColumnModelDefine column) {
        this.modifyColumnList.add(column);
        this.allColumnsMap.put(colCode, column);
    }

    public boolean needCre() {
        return !this.createColumnList.isEmpty();
    }

    public boolean needMod() {
        return !this.modifyColumnList.isEmpty();
    }

    public boolean needDel() {
        return !this.deleteColumnList.isEmpty();
    }

    public DesignColumnModelDefine getColBefore(String columnCode) {
        return this.allOldColumnsMap.get(columnCode);
    }

    public DesignColumnModelDefine[] getCreColumns() {
        return this.createColumnList.toArray(new DesignColumnModelDefine[0]);
    }

    public DesignColumnModelDefine[] getModColumns() {
        return this.modifyColumnList.toArray(new DesignColumnModelDefine[0]);
    }

    public String[] getDelColumns() {
        return (String[])this.deleteColumnList.stream().map(IModelDefineItem::getID).toArray(String[]::new);
    }

    public void finishSetting() {
        if (!CollectionUtils.isEmpty(this.allOldColumnsMap)) {
            HashSet<String> delColCodes = new HashSet<String>(this.allOldColumnsMap.keySet());
            delColCodes.removeAll(this.createColumnList.stream().map(IModelDefineItem::getCode).collect(Collectors.toSet()));
            delColCodes.removeAll(this.modifyColumnList.stream().map(IModelDefineItem::getCode).collect(Collectors.toSet()));
            delColCodes.forEach(o -> this.deleteColumnList.add(this.allOldColumnsMap.get(o)));
        }
    }

    public DesignColumnModelDefine getColLatest(String colCode) {
        return this.allColumnsMap.get(colCode);
    }
}

