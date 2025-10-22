/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.bean;

import com.jiuqi.nr.common.itree.INode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="BaseDataInfo", description="\u57fa\u7840\u6570\u636e\u6761\u76ee")
public class BaseDataInfo
implements INode {
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76eeid(key)", name="id")
    private String key;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76eecode", name="code")
    private String code;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u6392\u5e8f", name="order")
    private double order;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u7236\u8282\u70b9", name="parentId")
    private String parentId;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u6839\u8def\u5f84\u5217\u8868", name="parents")
    private List<String> parents = new ArrayList<String>();
    @ApiModelProperty(value="\u662f\u5426\u7ee7\u7eed\u5206\u9875", name="continuePaging")
    private boolean continuePaging = false;
    @ApiModelProperty(value="\u76f4\u63a5\u4e0b\u7ea7\u6570\u91cf", name="totalCount")
    private int totalCount;
    @ApiModelProperty(value="\u5f53\u524d\u5206\u9875\u9875\u6570", name="currentPage")
    private int currentPage;
    @ApiModelProperty(value="\u5b9e\u4f53\u5c5e\u6027\u548c\u503c\u7684map", name="codeDataMap")
    private Map<String, String> codeDataMap = new LinkedHashMap<String, String>();

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double order) {
        this.order = order;
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

    public Map<String, String> getCodeDataMap() {
        return this.codeDataMap;
    }

    public void setCodeDataMap(Map<String, String> codeDataMap) {
        this.codeDataMap = codeDataMap;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isContinuePaging() {
        return this.continuePaging;
    }

    public void setContinuePaging(boolean continuePaging) {
        this.continuePaging = continuePaging;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

