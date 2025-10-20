/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.vo;

import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO;
import java.util.List;

public class ArbitrarilyMergeQuerySchemeVO {
    private String id;
    private String resourceId;
    private String schemeName;
    private String userId;
    private String settingJson;
    private String externalField;
    private String finalFormula;
    private Double orderNum;
    private List<ArbitrarilyMergeOrgFilterSettingVO> settingList;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ArbitrarilyMergeOrgFilterSettingVO> getSettingList() {
        return this.settingList;
    }

    public void setSettingList(List<ArbitrarilyMergeOrgFilterSettingVO> settingList) {
        this.settingList = settingList;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSettingJson() {
        return this.settingJson;
    }

    public void setSettingJson(String settingJson) {
        this.settingJson = settingJson;
    }

    public String getExternalField() {
        return this.externalField;
    }

    public void setExternalField(String externalField) {
        this.externalField = externalField;
    }

    public String getFinalFormula() {
        return this.finalFormula;
    }

    public void setFinalFormula(String finalFormula) {
        this.finalFormula = finalFormula;
    }

    public Double getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Double orderNum) {
        this.orderNum = orderNum;
    }
}

