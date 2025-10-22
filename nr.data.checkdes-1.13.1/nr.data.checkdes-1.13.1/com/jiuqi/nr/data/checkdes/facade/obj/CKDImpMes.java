/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.AbstractMessage
 *  com.jiuqi.nr.data.common.Message
 */
package com.jiuqi.nr.data.checkdes.facade.obj;

import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailedInfo;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpSuccessInfo;
import com.jiuqi.nr.data.checkdes.facade.obj.InvalidData;
import com.jiuqi.nr.data.common.AbstractMessage;
import com.jiuqi.nr.data.common.Message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CKDImpMes
extends AbstractMessage
implements Message<CKDImpMes>,
Serializable {
    private static final long serialVersionUID = -8375106629635248308L;
    private List<String> formulaSchemeKeys;
    private List<ImpFailedInfo> failedInfos = new ArrayList<ImpFailedInfo>();
    private List<InvalidData> invalidDataList = new ArrayList<InvalidData>();
    private List<ImpSuccessInfo> successInfos = new ArrayList<ImpSuccessInfo>();

    public List<String> getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(List<String> formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public CKDImpMes getMessage() {
        return this;
    }

    public List<ImpFailedInfo> getFailedInfos() {
        return this.failedInfos;
    }

    public void setFailedInfos(List<ImpFailedInfo> failedInfos) {
        this.failedInfos = failedInfos;
    }

    public List<InvalidData> getInvalidDataList() {
        return this.invalidDataList;
    }

    public void setInvalidDataList(List<InvalidData> invalidDataList) {
        this.invalidDataList = invalidDataList;
    }

    public List<ImpSuccessInfo> getSuccessInfos() {
        return this.successInfos;
    }

    public void setSuccessInfos(List<ImpSuccessInfo> successInfos) {
        this.successInfos = successInfos;
    }
}

