/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.intf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.BizModelExtFieldInfo;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.intf.BaseFetchTaskInfo;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FetchTaskContext
extends BaseFetchTaskInfo {
    private String formId;
    private List<FetchSettingVO> fetchSettingList;
    private FloatRegionConfigVO floatRegionConfigVO;
    private BizModelExtFieldInfo bizModelExtFieldInfo;

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<FetchSettingVO> getFetchSettingList() {
        return this.fetchSettingList;
    }

    public void setFetchSettingList(List<FetchSettingVO> fetchSettingList) {
        this.fetchSettingList = fetchSettingList;
    }

    public FloatRegionConfigVO getFloatRegionConfigVO() {
        return this.floatRegionConfigVO;
    }

    public void setFloatRegionConfigVO(FloatRegionConfigVO floatRegionConfigVO) {
        this.floatRegionConfigVO = floatRegionConfigVO;
    }

    public BizModelExtFieldInfo getBizModelExtFieldInfo() {
        return this.bizModelExtFieldInfo;
    }

    public void setBizModelExtFieldInfo(BizModelExtFieldInfo bizModelExtFieldInfo) {
        this.bizModelExtFieldInfo = bizModelExtFieldInfo;
    }

    @Override
    public String toString() {
        return "FetchTaskContext{formId='" + this.formId + '\'' + ", fetchSettingList=" + this.fetchSettingList + ", floatRegionConfigVO=" + this.floatRegionConfigVO + ", bizModelExtFieldInfo=" + this.bizModelExtFieldInfo + '}';
    }
}

