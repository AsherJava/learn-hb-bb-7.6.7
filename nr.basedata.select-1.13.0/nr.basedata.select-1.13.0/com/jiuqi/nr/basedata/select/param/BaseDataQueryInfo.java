/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.param;

import com.jiuqi.nr.basedata.select.bean.BaseDataLinkage;
import com.jiuqi.nr.basedata.select.bean.BaseDataSelectFilterInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModel(value="BaseDataQueryInfo", description="\u57fa\u7840\u6570\u636e\u8bf7\u6c42\u53c2\u6570")
public class BaseDataQueryInfo {
    @ApiModelProperty(value="\u4efb\u52a1key", name="taskKey", required=true)
    private String taskKey;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848Key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u5f53\u524d\u7ef4\u5ea6", name="dimensionSet", required=true)
    private Map<String, DimensionValue> dimensionSet;
    @ApiModelProperty(value="\u5b9e\u4f53key", name="entityKey", required=true)
    private String entityKey;
    @ApiModelProperty(value="\u57fa\u7840\u6570\u636e\u5b9e\u4f53\u4ee3\u7801(\u5b9a\u4f4d\u7528)", name="entityKeyData")
    private String entityKeyData;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u7236\u8282\u70b9\u4e3b\u952e", name="parentKey")
    private String parentKey = "root";
    @ApiModelProperty(value="\u5206\u9875\u4fe1\u606f", name="pagerInfo")
    private PagerInfo pagerInfo;
    @ApiModelProperty(value="\u662f\u5426\u7ee7\u7eed\u67e5\u8be2\u6240\u6709\u5b50\u8282\u70b9(false\u53ea\u67e5\u76f4\u63a5\u4e0b\u7ea7\uff0ctrue\u6240\u6709\u4e0b\u7ea7)", name="allChildren")
    private boolean allChildren = false;
    @ApiModelProperty(value="\u662f\u5426\u53ea\u67e5\u8be2\u53f6\u5b50\u8282\u70b9", name="searchLeaf")
    private boolean searchLeaf = false;
    @ApiModelProperty(value="\u67e5\u8be2\u7ed3\u679c\u662f\u5426\u6392\u5e8f", name="sorted")
    private boolean sorted = true;
    @ApiModelProperty(value="\u4e0b\u62c9\u5b57\u6bb5", name="dropDownFields")
    private List<String> dropDownFields = new ArrayList<String>();
    @ApiModelProperty(value="\u5b57\u6bb5\u5217\u8868(\u60f3\u8981\u663e\u793a\u7684\u5b57\u6bb5)", name="captionFields")
    private List<String> fieldList = new ArrayList<String>();
    @ApiModelProperty(value="\u662f\u5426\u5224\u65ad\u679a\u4e3e\u6761\u76ee\u53ea\u8bfb\u6743\u9650", name="readAuth")
    private boolean readAuth = true;
    @ApiModelProperty(value="\u5b9e\u4f53\u67e5\u8be2\u6743\u9650", name="entityAuth")
    private boolean entityAuth = true;
    @ApiModelProperty(value="\u5b9e\u4f53\u8fc7\u6761\u4ef6", name="rowFilter")
    private String rowFilter;
    @ApiModelProperty(value="\u57fa\u7840\u6570\u636e\u8054\u52a8\u53c2\u6570", name="baseDataLinkage")
    private List<BaseDataLinkage> baseDataLinkageList = new ArrayList<BaseDataLinkage>();
    @ApiModelProperty(value="\u8054\u52a8\u7c7b\u578b\uff1a0\u4e0d\u8054\u52a8\uff0c1\u57fa\u7840\u6570\u636e\u8054\u52a8\uff0c2\u7ea7\u6b21\u8054\u52a8\u76f8\u540c\u57fa\u7840\u6570\u636e\uff0c3\u7ea7\u6b21\u8054\u52a8\u4e0d\u540c\u57fa\u7840\u6570\u636e", name="linkageType")
    private String linkageType = "0";
    @ApiModelProperty(value="\u6ce8\u518c\u7684\u8fc7\u6ee4\u5668", name="baseDataSelectFilterInfos")
    private List<BaseDataSelectFilterInfo> baseDataSelectFilterInfos = new ArrayList<BaseDataSelectFilterInfo>();
    @ApiModelProperty(value="\u5b9e\u4f53\u5b57\u6bb5\u8131\u654f", name="desensitized")
    private boolean desensitized = false;

    public boolean isSorted() {
        return this.sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityViewKey) {
        this.entityKey = entityViewKey;
    }

    public String getEntityKeyData() {
        return this.entityKeyData;
    }

    public void setEntityKeyData(String entityKeyData) {
        this.entityKeyData = entityKeyData;
    }

    public boolean isReadAuth() {
        return this.readAuth;
    }

    public void setReadAuth(boolean readAuth) {
        this.readAuth = readAuth;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public boolean isAllChildren() {
        return this.allChildren;
    }

    public void setAllChildren(boolean allChildren) {
        this.allChildren = allChildren;
    }

    public List<String> getDropDownFields() {
        return this.dropDownFields;
    }

    public void setDropDownFields(List<String> dropDownFields) {
        this.dropDownFields = dropDownFields;
    }

    public List<String> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    public boolean isSearchLeaf() {
        return this.searchLeaf;
    }

    public void setSearchLeaf(boolean searchLeaf) {
        this.searchLeaf = searchLeaf;
    }

    public List<BaseDataLinkage> getBaseDataLinkageList() {
        return this.baseDataLinkageList;
    }

    public void setBaseDataLinkageList(List<BaseDataLinkage> baseDataLinkageList) {
        this.baseDataLinkageList = baseDataLinkageList;
    }

    public boolean isEntityAuth() {
        return this.entityAuth;
    }

    public void setEntityAuth(boolean entityAuth) {
        this.entityAuth = entityAuth;
    }

    public String getRowFilter() {
        return this.rowFilter;
    }

    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
    }

    public String getLinkageType() {
        return this.linkageType;
    }

    public void setLinkageType(String linkageType) {
        this.linkageType = linkageType;
    }

    public List<BaseDataSelectFilterInfo> getBaseDataSelectFilterInfos() {
        return this.baseDataSelectFilterInfos;
    }

    public void setBaseDataSelectFilterInfos(List<BaseDataSelectFilterInfo> baseDataSelectFilterInfos) {
        this.baseDataSelectFilterInfos = baseDataSelectFilterInfos;
    }

    public boolean isDesensitized() {
        return this.desensitized;
    }

    public void setDesensitized(boolean desensitized) {
        this.desensitized = desensitized;
    }
}

