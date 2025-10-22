/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.nr.definition.analysis.vo.AnalysisVersionVO
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.definition.analysis.vo.AnalysisVersionVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;

@ApiModel(value="EntityData", description="\u4e3b\u4f53\u6570\u636e\u679a\u4e3e\u6761\u76ee")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class EntityData
implements Serializable {
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76eeid", name="id")
    private String id;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u663e\u793a\u6807\u9898", name="rowCaption")
    private String rowCaption;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76eecode", name="code")
    private String code;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u6392\u5e8f", name="order")
    private double order;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u53f6\u5b50\u8282\u70b9", name="leaf")
    private boolean leaf;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u5b50\u8282\u70b9\u6570\u76ee", name="childrenCount")
    private int childrenCount;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u7236\u8282\u70b9", name="parentId")
    private String parentId;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u6839\u8def\u5f84\u5217\u8868", name="parents")
    private List<String> parents = new ArrayList<String>();
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u5176\u4ed6\u5c5e\u6027", name="data")
    private List<String> data = new ArrayList<String>();
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u5b50\u8282\u70b9\u5217\u8868", name="children")
    private List<EntityData> children = new ArrayList<EntityData>();
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u5b50\u8282\u70b9\u5217\u8868\uff08\u80fd\u5220\u4e0d\uff09", name="children")
    private AnalysisVersionVO analysisVersion;
    @ApiModelProperty(value="\u5e01\u79cd\u60c5\u666f\u662f\u5426\u4e3a\u672c\u4f4d\u5e01", name="baseCurrency")
    private boolean baseCurrency = false;
    @ApiModelProperty(value="\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u5173\u8054\u4e86\u53e6\u4e00\u4e2a\u57fa\u7840\u6570\u636e\u663e\u793acode|title", name="referCodeData")
    private Map<String, String> referData = new HashedMap<String, String>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRowCaption() {
        return this.rowCaption;
    }

    public void setRowCaption(String rowCaption) {
        this.rowCaption = rowCaption;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public int getChildrenCount() {
        return this.childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<String> getParents() {
        return this.parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public List<String> getData() {
        return this.data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<EntityData> getChildren() {
        return this.children;
    }

    public void setChildren(List<EntityData> children) {
        this.children = children;
    }

    public AnalysisVersionVO getAnalysisVersion() {
        return this.analysisVersion;
    }

    public void setAnalysisVersion(AnalysisVersionVO analysisVersion) {
        this.analysisVersion = analysisVersion;
    }

    public boolean isBaseCurrency() {
        return this.baseCurrency;
    }

    public void setBaseCurrency(boolean baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Map<String, String> getReferData() {
        return this.referData;
    }

    public void setReferData(Map<String, String> referData) {
        this.referData = referData;
    }
}

