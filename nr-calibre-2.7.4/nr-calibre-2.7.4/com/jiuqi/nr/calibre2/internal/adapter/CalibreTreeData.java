/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.adapter;

import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreDataBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CalibreTreeData
extends CalibreDataBase {
    List<CalibreDataDTO> rootRows;
    HashMap<String, ArrayList<CalibreDataDTO>> childrenRows;
    HashMap<String, ArrayList<CalibreDataDTO>> notExsitRows;
    LinkedHashMap<String, CalibreDataDTO> entityKeySearch;
    private final List<CalibreDataDTO> allRows;

    public CalibreTreeData(List<CalibreDataDTO> allRows) {
        this.allRows = allRows;
        this.rootRows = new ArrayList<CalibreDataDTO>();
        this.childrenRows = new HashMap();
        this.notExsitRows = new HashMap();
        this.buildNormalTree();
    }

    private void buildEntityKeySearch() {
        this.entityKeySearch = new LinkedHashMap();
        for (CalibreDataDTO dataRow : this.allRows) {
            this.entityKeySearch.put(dataRow.getCode(), dataRow);
        }
    }

    private void buildNormalTree() {
        this.buildEntityKeySearch();
        for (CalibreDataDTO calibreData : this.allRows) {
            String parentKey = calibreData.getParent();
            if (parentKey == null || parentKey.length() <= 0) {
                this.rootRows.add(calibreData);
                continue;
            }
            CalibreDataDTO parentRow = this.entityKeySearch.get(parentKey);
            if (parentRow == null) {
                ArrayList<Object> children;
                this.rootRows.add(calibreData);
                if (!this.notExsitRows.containsKey(parentKey)) {
                    children = new ArrayList();
                    this.notExsitRows.put(parentKey, children);
                } else {
                    children = this.notExsitRows.get(parentKey);
                }
                children.add(calibreData);
                continue;
            }
            if (parentKey.equals(calibreData.getCode())) {
                this.rootRows.add(calibreData);
                continue;
            }
            this.addToChildrenRows(calibreData, parentKey);
        }
    }

    private void addToChildrenRows(CalibreDataDTO entityRowImpl, String parentKey) {
        ArrayList<Object> children;
        if (!this.childrenRows.containsKey(parentKey)) {
            children = new ArrayList();
            this.childrenRows.put(parentKey, children);
        } else {
            children = this.childrenRows.get(parentKey);
        }
        children.add(entityRowImpl);
    }

    public List<CalibreDataDTO> getRootRows() {
        return this.rootRows;
    }

    @Override
    public List<CalibreDataDTO> getChildRows(String entityKeyData) {
        return this.directGetChildRows(entityKeyData);
    }

    @Override
    public List<CalibreDataDTO> getAllChildRows(String entityKeyData) {
        return this.directGetAllChildRows(entityKeyData);
    }

    @Override
    public List<CalibreDataDTO> internalGetChildRows(String entityKeyData) {
        ArrayList<CalibreDataDTO> childRows = new ArrayList<CalibreDataDTO>();
        if (this.childrenRows.containsKey(entityKeyData)) {
            childRows.addAll((Collection)this.childrenRows.get(entityKeyData));
        }
        if (this.notExsitRows.containsKey(entityKeyData)) {
            childRows.addAll((Collection<CalibreDataDTO>)this.notExsitRows.get(entityKeyData));
        }
        return childRows;
    }

    @Override
    protected List<CalibreDataDTO> directGetChildRows(String entityKeyData) {
        List childRows = this.childrenRows.get(entityKeyData);
        return childRows == null ? new ArrayList() : childRows;
    }
}

