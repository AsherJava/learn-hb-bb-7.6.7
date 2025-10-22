/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.data.logic.api.param.ckdcopy;

import com.jiuqi.nr.data.logic.api.param.CheckDesFmlObj;
import com.jiuqi.nr.data.logic.spi.ICKDCopyOptionProvider;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import java.util.List;
import java.util.Map;

public class UnsupportHandleParam {
    private String srcFormSchemeKey;
    private String dstFormSchemeKey;
    private String srcFmlSchemeKey;
    private String dstFmlSchemeKey;
    private List<CheckDesFmlObj> unsupportedSrcDes;
    private Map<String, List<CheckDesFmlObj>> unsupportedDstDesMap;
    private ICKDCopyOptionProvider ckdCopyOptionProvider;
    private IProviderStore providerStore;

    public ICKDCopyOptionProvider getCkdCopyOptionProvider() {
        return this.ckdCopyOptionProvider;
    }

    public void setCkdCopyOptionProvider(ICKDCopyOptionProvider ckdCopyOptionProvider) {
        this.ckdCopyOptionProvider = ckdCopyOptionProvider;
    }

    public String getDstFmlSchemeKey() {
        return this.dstFmlSchemeKey;
    }

    public void setDstFmlSchemeKey(String dstFmlSchemeKey) {
        this.dstFmlSchemeKey = dstFmlSchemeKey;
    }

    public String getDstFormSchemeKey() {
        return this.dstFormSchemeKey;
    }

    public void setDstFormSchemeKey(String dstFormSchemeKey) {
        this.dstFormSchemeKey = dstFormSchemeKey;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public String getSrcFmlSchemeKey() {
        return this.srcFmlSchemeKey;
    }

    public void setSrcFmlSchemeKey(String srcFmlSchemeKey) {
        this.srcFmlSchemeKey = srcFmlSchemeKey;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public Map<String, List<CheckDesFmlObj>> getUnsupportedDstDesMap() {
        return this.unsupportedDstDesMap;
    }

    public void setUnsupportedDstDesMap(Map<String, List<CheckDesFmlObj>> unsupportedDstDesMap) {
        this.unsupportedDstDesMap = unsupportedDstDesMap;
    }

    public List<CheckDesFmlObj> getUnsupportedSrcDes() {
        return this.unsupportedSrcDes;
    }

    public void setUnsupportedSrcDes(List<CheckDesFmlObj> unsupportedSrcDes) {
        this.unsupportedSrcDes = unsupportedSrcDes;
    }
}

