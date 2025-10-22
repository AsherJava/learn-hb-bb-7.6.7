/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.datacrud.impl.service.impl;

import com.jiuqi.nr.datacrud.impl.service.EntityDataService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityDataLoader {
    private boolean filter;
    private DimensionCombination masterKey;
    private String periodView;
    private final EntityDataService entityDataService;
    private final Map<String, IEntityTable> tableMap = new HashMap<String, IEntityTable>();
    private final Map<String, Map<String, IEntityRow>> valueMaps = new HashMap<String, Map<String, IEntityRow>>();
    private final Map<String, Map<String, IEntityRow>> valueCodeMaps = new HashMap<String, Map<String, IEntityRow>>();
    private final Map<String, Map<String, Integer>> childrenCountMaps = new HashMap<String, Map<String, Integer>>();
    private final Map<String, Set<String>> nullMaps = new HashMap<String, Set<String>>();

    public EntityDataLoader(EntityDataService entityDataService) {
        this.entityDataService = entityDataService;
    }

    public void setMasterKey(DimensionCombination masterKey) {
        this.masterKey = masterKey;
    }

    public DimensionCombination getMasterKey() {
        return this.masterKey;
    }

    public String getPeriodView() {
        return this.periodView;
    }

    public void setPeriodView(String periodView) {
        this.periodView = periodView;
    }

    public IEntityRow getEntityRowByKey(String entityId, String key) {
        Map valueMap;
        IEntityRow entityRow;
        IEntityTable table;
        String cacheKey = entityId;
        if (this.masterKey != null) {
            cacheKey = cacheKey + "_" + this.masterKey;
        }
        if ((table = this.tableMap.get(cacheKey)) == null) {
            table = this.entityDataService.getEntityTable(entityId, this.masterKey, this.periodView);
            this.tableMap.put(cacheKey, table);
        }
        if ((entityRow = (IEntityRow)(valueMap = this.valueMaps.computeIfAbsent(cacheKey, k -> new HashMap())).get(key)) == null) {
            Set nullValues = this.nullMaps.computeIfAbsent(cacheKey, k -> new HashSet());
            if (nullValues.contains(key)) {
                return null;
            }
            entityRow = this.filter ? table.quickFindByEntityKey(key) : table.findByEntityKey(key);
            if (entityRow == null) {
                nullValues.add(key);
                return null;
            }
            valueMap.put(key, entityRow);
        }
        return entityRow;
    }

    public IEntityRow getEntityRowByCode(String entityId, String code) {
        Map valueCodeMap;
        IEntityRow entityRow;
        IEntityTable table;
        String cacheKey = entityId;
        if (this.masterKey != null) {
            cacheKey = cacheKey + "_" + this.masterKey;
        }
        if ((table = this.tableMap.get(cacheKey)) == null) {
            table = this.entityDataService.getEntityTable(cacheKey, this.masterKey, this.periodView);
            this.tableMap.put(cacheKey, table);
        }
        if ((entityRow = (IEntityRow)(valueCodeMap = this.valueCodeMaps.computeIfAbsent(cacheKey, k -> new HashMap())).get(code)) == null) {
            Set nullValues = this.nullMaps.computeIfAbsent(cacheKey + "-code", k -> new HashSet());
            if (nullValues.contains(code)) {
                return null;
            }
            entityRow = table.findByCode(code);
            if (entityRow == null) {
                nullValues.add(code);
                return null;
            }
            valueCodeMap.put(code, entityRow);
        }
        return entityRow;
    }

    public int getDirectChildCount(String entityId, String key) {
        Map countMap;
        Integer count;
        IEntityTable table;
        String cacheKey = entityId;
        if (this.masterKey != null) {
            cacheKey = cacheKey + "_" + this.masterKey;
        }
        if ((table = this.tableMap.get(cacheKey)) == null) {
            table = this.entityDataService.getEntityTable(cacheKey, this.masterKey, this.periodView);
            this.tableMap.put(cacheKey, table);
        }
        if ((count = (Integer)(countMap = this.childrenCountMaps.computeIfAbsent(cacheKey, k -> new HashMap())).get(key)) == null) {
            int directChildCount = table.getDirectChildCount(key);
            countMap.put(key, directChildCount);
            return directChildCount;
        }
        return count;
    }
}

