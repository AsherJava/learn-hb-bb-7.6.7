/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.adapter;

import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public abstract class CalibreDataBase {
    protected Map<String, Map<String, Integer>> allChildrenCount;
    protected Map<String, Map<String, Integer>> directChildrenCount = new HashMap<String, Map<String, Integer>>();

    public CalibreDataBase() {
        this.allChildrenCount = new HashMap<String, Map<String, Integer>>();
    }

    public abstract List<CalibreDataDTO> getChildRows(String var1);

    public abstract List<CalibreDataDTO> getAllChildRows(String var1);

    public abstract List<CalibreDataDTO> internalGetChildRows(String var1);

    protected abstract List<CalibreDataDTO> directGetChildRows(String var1);

    public List<CalibreDataDTO> directGetAllChildRows(String entityKeyData) {
        List<CalibreDataDTO> children = this.directGetChildRows(entityKeyData);
        if (children.size() <= 0) {
            return new ArrayList<CalibreDataDTO>();
        }
        Stack<CalibreDataDTO> childrenStack = new Stack<CalibreDataDTO>();
        for (int childIndex = children.size() - 1; childIndex >= 0; --childIndex) {
            childrenStack.push(children.get(childIndex));
        }
        ArrayList<CalibreDataDTO> resultRows = new ArrayList<CalibreDataDTO>();
        while (childrenStack.size() > 0) {
            CalibreDataDTO entityRow = (CalibreDataDTO)childrenStack.pop();
            resultRows.add(entityRow);
            children = this.directGetChildRows(entityRow.getCode());
            if (children.size() <= 0) continue;
            for (int childIndex = children.size() - 1; childIndex >= 0; --childIndex) {
                childrenStack.push(children.get(childIndex));
            }
        }
        return resultRows;
    }

    public int getDirectChildCount(String entityKeyData) {
        List<CalibreDataDTO> childRows = this.getChildRows(entityKeyData);
        return childRows.size();
    }

    public int getAllChildCount(String entityKeyData) {
        List<CalibreDataDTO> childRows = this.getAllChildRows(entityKeyData);
        return childRows.size();
    }

    public Map<String, Integer> getDirectChildCountByParent(String parentKey) {
        Map<String, Integer> childCount = this.directChildrenCount.get(parentKey);
        if (childCount != null) {
            return childCount;
        }
        List<CalibreDataDTO> childRows = this.getChildRows(parentKey);
        HashMap<String, Integer> resultMap = new HashMap<String, Integer>(childRows.size());
        for (CalibreDataDTO iEntityRow : childRows) {
            String entityKey = iEntityRow.getCode();
            List<CalibreDataDTO> directRows = this.directGetChildRows(entityKey);
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
        List<CalibreDataDTO> childRows = this.getChildRows(parentKey);
        HashMap<String, Integer> resultMap = new HashMap<String, Integer>(childRows.size());
        for (CalibreDataDTO iEntityRow : childRows) {
            String entityKey = iEntityRow.getCode();
            List<CalibreDataDTO> allChildRows = this.directGetAllChildRows(entityKey);
            resultMap.put(entityKey, allChildRows.size());
        }
        this.allChildrenCount.put(parentKey, resultMap);
        return resultMap;
    }
}

