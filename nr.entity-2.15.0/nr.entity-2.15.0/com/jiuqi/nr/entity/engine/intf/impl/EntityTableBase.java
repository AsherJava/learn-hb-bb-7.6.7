/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.executors.QueryInfo;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.springframework.util.CollectionUtils;

public abstract class EntityTableBase
extends ReadonlyTableImpl {
    protected Map<String, Map<String, Integer>> allChildrenCount;
    protected Map<String, Map<String, Integer>> directChildrenCount = new HashMap<String, Map<String, Integer>>();

    public EntityTableBase(QueryContext queryContext, QueryInfo queryInfo) {
        super(queryContext, queryInfo);
        this.allChildrenCount = new HashMap<String, Map<String, Integer>>();
    }

    public abstract List<IEntityRow> getChildRows(String var1);

    public abstract List<IEntityRow> getAllChildRows(String var1);

    protected abstract List<IEntityRow> directGetChildRows(String var1);

    public List<IEntityRow> directGetAllChildRows(String entityKeyData) {
        List<IEntityRow> children = this.directGetChildRows(entityKeyData);
        if (CollectionUtils.isEmpty(children)) {
            return new ArrayList<IEntityRow>();
        }
        Stack<IEntityRow> childrenStack = new Stack<IEntityRow>();
        for (int childIndex = children.size() - 1; childIndex >= 0; --childIndex) {
            childrenStack.push(children.get(childIndex));
        }
        ArrayList<IEntityRow> resultRows = new ArrayList<IEntityRow>();
        while (!CollectionUtils.isEmpty(childrenStack)) {
            IEntityRow entityRow = (IEntityRow)childrenStack.pop();
            resultRows.add(entityRow);
            children = this.directGetChildRows(entityRow.getEntityKeyData());
            if (CollectionUtils.isEmpty(children)) continue;
            for (int childIndex = children.size() - 1; childIndex >= 0; --childIndex) {
                childrenStack.push(children.get(childIndex));
            }
        }
        return resultRows;
    }

    public int getDirectChildCount(String entityKeyData) {
        List<IEntityRow> childRows = this.getChildRows(entityKeyData);
        return childRows.size();
    }

    public int getAllChildCount(String entityKeyData) {
        List<IEntityRow> childRows = this.getAllChildRows(entityKeyData);
        return childRows.size();
    }

    public Map<String, Integer> getDirectChildCountByParent(String parentKey) {
        Map<String, Integer> childCount = this.directChildrenCount.get(parentKey);
        if (childCount != null) {
            return childCount;
        }
        List<IEntityRow> childRows = this.getChildRows(parentKey);
        HashMap<String, Integer> resultMap = new HashMap<String, Integer>(childRows.size());
        for (IEntityRow iEntityRow : childRows) {
            String entityKey = iEntityRow.getEntityKeyData();
            List<IEntityRow> directRows = this.directGetChildRows(entityKey);
            resultMap.put(entityKey, directRows.size());
        }
        this.directChildrenCount.put(parentKey, resultMap);
        return resultMap;
    }

    public Map<String, Integer> getAllChildCountByParent(String parentKey) {
        Map<String, Integer> childCount = this.allChildrenCount.get(parentKey);
        if (childCount != null) {
            return childCount;
        }
        List<IEntityRow> childRows = this.getChildRows(parentKey);
        HashMap<String, Integer> resultMap = new HashMap<String, Integer>(childRows.size());
        for (IEntityRow iEntityRow : childRows) {
            String entityKey = iEntityRow.getEntityKeyData();
            List<IEntityRow> allChildRows = this.directGetAllChildRows(entityKey);
            resultMap.put(entityKey, allChildRows.size());
        }
        this.allChildrenCount.put(parentKey, resultMap);
        return resultMap;
    }
}

