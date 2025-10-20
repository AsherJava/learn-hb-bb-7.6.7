/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.vo;

import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import java.util.List;

public class DataSchemeVO {
    private String code;
    private String name;
    private List<SelectOptionVO> sourceDataType;
    private Boolean needEtlJob;
    private List<SelectOptionVO> ruleTypeList;

    public DataSchemeVO() {
    }

    public DataSchemeVO(String code, String name, List<SelectOptionVO> sourceDataType, Boolean needEtlJob, List<SelectOptionVO> ruleTypeList) {
        this.code = code;
        this.name = name;
        this.sourceDataType = sourceDataType;
        this.needEtlJob = needEtlJob;
        this.ruleTypeList = ruleTypeList;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectOptionVO> getSourceDataType() {
        return this.sourceDataType;
    }

    public void setSourceDataType(List<SelectOptionVO> sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public Boolean getNeedEtlJob() {
        return this.needEtlJob;
    }

    public void setNeedEtlJob(Boolean needEtlJob) {
        this.needEtlJob = needEtlJob;
    }

    public List<SelectOptionVO> getRuleTypeList() {
        return this.ruleTypeList;
    }

    public void setRuleTypeList(List<SelectOptionVO> ruleTypeList) {
        this.ruleTypeList = ruleTypeList;
    }
}

