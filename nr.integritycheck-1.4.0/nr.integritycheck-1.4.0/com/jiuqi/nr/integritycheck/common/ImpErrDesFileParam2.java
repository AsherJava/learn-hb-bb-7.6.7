/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class ImpErrDesFileParam2 {
    private ParamsMapping mapping;
    private IProviderStore providerStore;
    private DimensionCollection dims;
    private List<String> formKeys;
    private boolean fullImport = false;
    private FilterDim filterDims;
    private CompletionDim completionDims;

    public ParamsMapping getMapping() {
        return this.mapping;
    }

    public void setMapping(ParamsMapping mapping) {
        this.mapping = mapping;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public DimensionCollection getDims() {
        return this.dims;
    }

    public void setDims(DimensionCollection dims) {
        this.dims = dims;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public boolean isFullImport() {
        return this.fullImport;
    }

    public void setFullImport(boolean fullImport) {
        this.fullImport = fullImport;
    }

    public FilterDim getFilterDims() {
        return this.filterDims;
    }

    public void setFilterDims(FilterDim filterDims) {
        this.filterDims = filterDims;
    }

    public CompletionDim getCompletionDims() {
        return this.completionDims;
    }

    public void setCompletionDims(CompletionDim completionDims) {
        this.completionDims = completionDims;
    }
}

