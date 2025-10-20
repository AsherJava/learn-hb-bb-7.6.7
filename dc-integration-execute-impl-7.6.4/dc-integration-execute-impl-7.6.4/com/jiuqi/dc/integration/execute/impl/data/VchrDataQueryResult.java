/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ArrayListMultimap
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.Table
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.dc.integration.execute.impl.data;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.jiuqi.common.base.util.UUIDUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VchrDataQueryResult {
    private LinkedHashMap<String, Integer> floatColumns = new LinkedHashMap();
    private List<Object[]> rowDatas = new ArrayList<Object[]>();
    private Map<String, List<Object[]>> voucherDatas = new HashMap<String, List<Object[]>>();
    private ArrayListMultimap<Object, String> itemByItemMap;
    private String fixedVchrId = UUIDUtils.newHalfGUIDStr();
    private Set<String> noMapVchrIdSet = new HashSet<String>();
    private Table<String, String, Set<String>> missMappingTable = HashBasedTable.create();

    public Map<String, List<Object[]>> getVoucherDatas() {
        return this.voucherDatas;
    }

    public void setVoucherDatas(Map<String, List<Object[]>> voucherDatas) {
        this.voucherDatas = voucherDatas;
    }

    public String getFixedVchrId() {
        return this.fixedVchrId;
    }

    public void setFixedVchrId(String fixedVchrId) {
        this.fixedVchrId = fixedVchrId;
    }

    public ArrayListMultimap<Object, String> getItemByItemMap() {
        return this.itemByItemMap;
    }

    public void setItemByItemMap(ArrayListMultimap<Object, String> itemByItemMap) {
        this.itemByItemMap = itemByItemMap;
    }

    public LinkedHashMap<String, Integer> getFloatColumns() {
        return this.floatColumns;
    }

    public void setFloatColumns(LinkedHashMap<String, Integer> floatColumns) {
        this.floatColumns = floatColumns;
    }

    public List<Object[]> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<Object[]> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public Set<String> getNoMapVchrIdSet() {
        return this.noMapVchrIdSet;
    }

    public void setNoMapVchrIdSet(Set<String> noMapVchrIdSet) {
        this.noMapVchrIdSet = noMapVchrIdSet;
    }

    public Table<String, String, Set<String>> getMissMappingTable() {
        return this.missMappingTable;
    }

    public void setMissMappingTable(Table<String, String, Set<String>> missMappingTable) {
        this.missMappingTable = missMappingTable;
    }
}

