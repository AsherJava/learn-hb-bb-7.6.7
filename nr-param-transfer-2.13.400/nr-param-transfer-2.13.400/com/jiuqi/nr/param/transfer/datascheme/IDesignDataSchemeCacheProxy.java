/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DataWrapper
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 */
package com.jiuqi.nr.param.transfer.datascheme;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DataWrapper;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.param.transfer.cache.TransferCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IDesignDataSchemeCacheProxy {
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private TransferCacheService transferCacheService;
    private final String CACHE_NAME = "DATA_SCHEME_CACHE_NAME";

    public DesignDataScheme getDataScheme(String key) {
        DesignDataScheme dataScheme;
        DataWrapper<DesignDataScheme> dataWrapper = this.transferCacheService.get("DATA_SCHEME_CACHE_NAME", key, DesignDataScheme.class);
        if (!dataWrapper.isPresent()) {
            dataScheme = this.iDesignDataSchemeService.getDataScheme(key);
            this.transferCacheService.put("DATA_SCHEME_CACHE_NAME", key, dataScheme);
        } else {
            dataScheme = (DesignDataScheme)dataWrapper.get();
        }
        return dataScheme;
    }

    public DesignDataGroup getDataGroup(String key) {
        DesignDataGroup dataGroup;
        DataWrapper<DesignDataGroup> dataWrapper = this.transferCacheService.get("DATA_SCHEME_CACHE_NAME", key, DesignDataGroup.class);
        if (!dataWrapper.isPresent()) {
            dataGroup = this.iDesignDataSchemeService.getDataGroup(key);
            this.transferCacheService.put("DATA_SCHEME_CACHE_NAME", key, dataGroup);
        } else {
            dataGroup = (DesignDataGroup)dataWrapper.get();
        }
        return dataGroup;
    }

    public DesignDataTable getDataTable(String key) {
        DesignDataTable dataTable;
        DataWrapper<DesignDataTable> dataWrapper = this.transferCacheService.get("DATA_SCHEME_CACHE_NAME", key, DesignDataTable.class);
        if (!dataWrapper.isPresent()) {
            dataTable = this.iDesignDataSchemeService.getDataTable(key);
            this.transferCacheService.put("DATA_SCHEME_CACHE_NAME", key, dataTable);
        } else {
            dataTable = (DesignDataTable)dataWrapper.get();
        }
        return dataTable;
    }

    public boolean isAddSchemeEntityRel(String dataSchemeKey) {
        DataWrapper<Boolean> booleanDataWrapper = this.transferCacheService.get("DATA_SCHEME_CACHE_NAME", dataSchemeKey + "-addSchemeRel", Boolean.class);
        if (booleanDataWrapper.isPresent()) {
            return (Boolean)booleanDataWrapper.get();
        }
        this.transferCacheService.put("DATA_SCHEME_CACHE_NAME", dataSchemeKey + "-addSchemeRel", true);
        return false;
    }

    public <DT extends DesignDataTable> void putDataTable(DT dataTable) {
        this.transferCacheService.put("DATA_SCHEME_CACHE_NAME", dataTable.getKey(), dataTable);
    }

    public <DG extends DesignDataGroup> void putDataGroup(DG dataGroup) {
        this.transferCacheService.put("DATA_SCHEME_CACHE_NAME", dataGroup.getKey(), dataGroup);
    }
}

