/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ImpSettings
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 */
package com.jiuqi.nr.io.tsd.dto;

import com.jiuqi.nr.data.common.service.ImpSettings;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import java.util.List;
import java.util.Map;

public class ImpSettingsImpl
implements ImpSettings {
    private DimensionCollection masterKeys;
    private List<String> nonexistentUnits;
    private Map<String, ImpMode> impModeMap;
    private FilterDim filterDim;
    private CompletionDim completionDim;

    public DimensionCollection getMasterKeys() {
        return this.masterKeys;
    }

    public List<String> getNonexistentUnits() {
        return this.nonexistentUnits;
    }

    public ImpMode getImpMode(String code) {
        if (this.impModeMap == null) {
            return ImpMode.FULL_BY_DATA;
        }
        return this.impModeMap.getOrDefault(code, ImpMode.FULL_BY_DATA);
    }

    public void resetMasterKeys(DimensionCollection masterKeys) {
        this.masterKeys = masterKeys;
    }

    public FilterDim getFilterDims() {
        return this.filterDim;
    }

    public CompletionDim getCompletionDims() {
        return this.completionDim;
    }

    public void setMasterKeys(DimensionCollection masterKeys) {
        this.masterKeys = masterKeys;
    }

    public void setNonexistentUnits(List<String> nonexistentUnits) {
        this.nonexistentUnits = nonexistentUnits;
    }

    public void setImpModeMap(Map<String, ImpMode> impModeMap) {
        this.impModeMap = impModeMap;
    }

    public void setFilterDim(FilterDim filterDim) {
        this.filterDim = filterDim;
    }

    public void setCompletionDim(CompletionDim completionDim) {
        this.completionDim = completionDim;
    }
}

