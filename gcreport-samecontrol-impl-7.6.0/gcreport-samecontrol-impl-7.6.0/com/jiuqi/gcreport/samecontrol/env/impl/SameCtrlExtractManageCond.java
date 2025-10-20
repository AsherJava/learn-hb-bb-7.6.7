/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 */
package com.jiuqi.gcreport.samecontrol.env.impl;

import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import java.util.List;

public class SameCtrlExtractManageCond {
    private GcOrgCenterService gcOrgCenterService;
    private SameCtrlChgOrgEO sameCtrlChgOrgEO;
    private List<SameCtrlChgOrgEO> sameCtrlChgOrgEOList;
    private SameCtrlSrcTypeEnum sameCtrlSrcTypeEnum;
    private List<SameCtrlSrcTypeEnum> sameCtrlSrcTypeEnumList;
    private SameCtrlSrcTypeEnum sameCtrlLastDateSrcTypeEnum;
    private String systemId;
    private String disposerCode;
    private String acquirerCode;
    private SameCtrlRuleTypeEnum sameCtrlRuleTypeEnum;

    public List<SameCtrlSrcTypeEnum> getSameCtrlSrcTypeEnumList() {
        return this.sameCtrlSrcTypeEnumList;
    }

    public void setSameCtrlSrcTypeEnumList(List<SameCtrlSrcTypeEnum> sameCtrlSrcTypeEnumList) {
        this.sameCtrlSrcTypeEnumList = sameCtrlSrcTypeEnumList;
    }

    public SameCtrlSrcTypeEnum getSameCtrlLastDateSrcTypeEnum() {
        return this.sameCtrlLastDateSrcTypeEnum;
    }

    public void setSameCtrlLastDateSrcTypeEnum(SameCtrlSrcTypeEnum sameCtrlLastDateSrcTypeEnum) {
        this.sameCtrlLastDateSrcTypeEnum = sameCtrlLastDateSrcTypeEnum;
    }

    public List<SameCtrlChgOrgEO> getSameCtrlChgOrgEOList() {
        return this.sameCtrlChgOrgEOList;
    }

    public void setSameCtrlChgOrgEOList(List<SameCtrlChgOrgEO> sameCtrlChgOrgEOList) {
        this.sameCtrlChgOrgEOList = sameCtrlChgOrgEOList;
    }

    public String getDisposerCode() {
        return this.disposerCode;
    }

    public void setDisposerCode(String disposerCode) {
        this.disposerCode = disposerCode;
    }

    public String getAcquirerCode() {
        return this.acquirerCode;
    }

    public void setAcquirerCode(String acquirerCode) {
        this.acquirerCode = acquirerCode;
    }

    public SameCtrlRuleTypeEnum getSameCtrlRuleTypeEnum() {
        return this.sameCtrlRuleTypeEnum;
    }

    public void setSameCtrlRuleTypeEnum(SameCtrlRuleTypeEnum sameCtrlRuleTypeEnum) {
        this.sameCtrlRuleTypeEnum = sameCtrlRuleTypeEnum;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public SameCtrlSrcTypeEnum getSameCtrlSrcTypeEnum() {
        return this.sameCtrlSrcTypeEnum;
    }

    public void setSameCtrlSrcTypeEnum(SameCtrlSrcTypeEnum sameCtrlSrcTypeEnum) {
        this.sameCtrlSrcTypeEnum = sameCtrlSrcTypeEnum;
    }

    public GcOrgCenterService getGcOrgCenterService() {
        return this.gcOrgCenterService;
    }

    public void setGcOrgCenterService(GcOrgCenterService gcOrgCenterService) {
        this.gcOrgCenterService = gcOrgCenterService;
    }

    public SameCtrlChgOrgEO getSameCtrlChgOrgEO() {
        return this.sameCtrlChgOrgEO;
    }

    public void setSameCtrlChgOrgEO(SameCtrlChgOrgEO sameCtrlChgOrgEO) {
        this.sameCtrlChgOrgEO = sameCtrlChgOrgEO;
    }
}

