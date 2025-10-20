/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.dto;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbParam;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class SumhbRegion {
    private String regionId;
    private String filter;
    private final List<DataFieldDeployInfo> allFields;
    private final List<SumhbParam> sumhbParams;

    public SumhbRegion(SumhbParam sumhbParam) {
        this.regionId = sumhbParam.getReginId();
        this.filter = sumhbParam.getRegionFilter();
        this.sumhbParams = new ArrayList<SumhbParam>();
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        List zbIds = runTimeViewController.getFieldKeysInRegion(this.regionId);
        DataFieldDeployInfoService dataFieldDeployInfoService = (DataFieldDeployInfoService)SpringContextUtils.getBean(DataFieldDeployInfoService.class);
        this.allFields = dataFieldDeployInfoService.getByDataFieldKeys(zbIds.toArray(new String[0]));
    }

    public boolean hasSumFilter() {
        return this.sumhbParams.stream().anyMatch(sumhbParam -> StringUtils.hasText(sumhbParam.getFilter()));
    }

    public boolean hasSumNoFilter() {
        return this.sumhbParams.stream().anyMatch(sumhbParam -> ObjectUtils.isEmpty(sumhbParam.getFilter()));
    }

    public void addSumhbParam(SumhbParam sumhbParam) {
        this.sumhbParams.add(sumhbParam);
    }

    public List<SumhbParam> getSumhbParams() {
        return this.sumhbParams;
    }

    public List<DataFieldDeployInfo> getAllFields() {
        return this.allFields;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String toString() {
        return "SumhbRegion{regionId='" + this.regionId + '\'' + ", filter='" + this.filter + '\'' + ", allFields=" + this.allFields + ", sumhbParams=" + this.sumhbParams + '}';
    }
}

