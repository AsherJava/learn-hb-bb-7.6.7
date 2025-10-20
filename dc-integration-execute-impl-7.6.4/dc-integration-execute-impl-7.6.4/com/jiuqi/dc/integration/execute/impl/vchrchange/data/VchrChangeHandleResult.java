/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.VchrMasterDim
 */
package com.jiuqi.dc.integration.execute.impl.vchrchange.data;

import com.jiuqi.dc.base.common.intf.impl.VchrMasterDim;
import java.util.List;

public class VchrChangeHandleResult {
    private List<String> srcVchrIdList;
    private List<VchrMasterDim> vchrDimList;
    private String log;

    public VchrChangeHandleResult() {
    }

    public VchrChangeHandleResult(List<String> srcVchrIdList, List<VchrMasterDim> vchrDimList, String log) {
        this.srcVchrIdList = srcVchrIdList;
        this.vchrDimList = vchrDimList;
        this.log = log;
    }

    public List<String> getSrcVchrIdList() {
        return this.srcVchrIdList;
    }

    public void setSrcVchrIdList(List<String> srcVchrIdList) {
        this.srcVchrIdList = srcVchrIdList;
    }

    public List<VchrMasterDim> getVchrDimList() {
        return this.vchrDimList;
    }

    public void setVchrDimList(List<VchrMasterDim> vchrDimList) {
        this.vchrDimList = vchrDimList;
    }

    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}

