/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.Table
 *  com.jiuqi.dc.base.common.cache.intf.CacheEntity
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.jiuqi.dc.base.common.cache.intf.CacheEntity;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;

public class DataRefCacheDTO
implements CacheEntity {
    private String schemeCode;
    private Table<String, String, DataRefDTO> dataRefTable = HashBasedTable.create();

    public DataRefCacheDTO(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getCacheKey() {
        return this.schemeCode;
    }

    public String getSchemeCode() {
        return this.schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public Table<String, String, DataRefDTO> getDataRefTable() {
        return this.dataRefTable;
    }

    public void setDataRefTable(Table<String, String, DataRefDTO> dataRefTable) {
        this.dataRefTable = dataRefTable;
    }
}

