/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.fmdm;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class BatchFMDMDTO
implements Serializable {
    private String formSchemeKey;
    private DimensionValueSet dimensionValueSet;
    private IContext context;
    private boolean ignoreCheckErrorData;
    private boolean isDataMasking = false;
    private List<FMDMDataDTO> fmdmList;
    private IProviderStore providerStore;
    private final Set<String> ignorePermissions = new HashSet<String>();

    public List<FMDMDataDTO> getFmdmList() {
        return this.fmdmList;
    }

    public void setFmdmList(List<FMDMDataDTO> fmdmList) {
        this.fmdmList = fmdmList;
    }

    public void addFmdmUpdateDTO(FMDMDataDTO fmdmDataDTO) {
        if (CollectionUtils.isEmpty(this.fmdmList)) {
            this.fmdmList = new ArrayList<FMDMDataDTO>();
        }
        this.fmdmList.add(fmdmDataDTO);
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public IContext getContext() {
        return this.context;
    }

    public void setContext(IContext context) {
        this.context = context;
    }

    public boolean isIgnoreCheckErrorData() {
        return this.ignoreCheckErrorData;
    }

    public void setIgnoreCheckErrorData(boolean ignoreCheckErrorData) {
        this.ignoreCheckErrorData = ignoreCheckErrorData;
    }

    public boolean isDataMasking() {
        return this.isDataMasking;
    }

    public void setDataMasking(boolean dataMasking) {
        this.isDataMasking = dataMasking;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public Set<String> getIgnorePermissions() {
        return this.ignorePermissions;
    }
}

