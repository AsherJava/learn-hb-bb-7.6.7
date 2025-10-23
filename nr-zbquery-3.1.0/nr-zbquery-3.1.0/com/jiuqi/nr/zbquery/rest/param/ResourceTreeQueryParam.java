/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package com.jiuqi.nr.zbquery.rest.param;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeNodeDTO;
import com.jiuqi.nr.zbquery.model.HiddenDimension;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceTreeQueryParam {
    private ResourceTreeNodeDTO dataTreeNode;
    private boolean allChildren;
    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type", visible=true)
    @JsonSubTypes(value={@JsonSubTypes.Type(value=QueryDimension.class, name="DIMENSION")})
    private QueryObject queryObject;
    private ZBQueryModel zbQueryModel;
    private Map<String, QueryDimensionType> relatedDimensionMap = new HashMap<String, QueryDimensionType>();
    private List<HiddenDimension> hiddenDimensions = new ArrayList<HiddenDimension>();

    public ResourceTreeNodeDTO getDataTreeNode() {
        return this.dataTreeNode;
    }

    public void setDataTreeNode(ResourceTreeNodeDTO dataTreeNode) {
        this.dataTreeNode = dataTreeNode;
    }

    public boolean isAllChildren() {
        return this.allChildren;
    }

    public void setAllChildren(boolean allChildren) {
        this.allChildren = allChildren;
    }

    public ZBQueryModel getZbQueryModel() {
        return this.zbQueryModel;
    }

    public void setZbQueryModel(ZBQueryModel zbQueryModel) {
        this.zbQueryModel = zbQueryModel;
    }

    public QueryObject getQueryObject() {
        return this.queryObject;
    }

    public void setQueryObject(QueryObject queryObject) {
        this.queryObject = queryObject;
    }

    public List<HiddenDimension> getHiddenDimensions() {
        return this.hiddenDimensions;
    }

    public void setHiddenDimensions(List<HiddenDimension> hiddenDimensions) {
        this.hiddenDimensions = hiddenDimensions;
    }

    public Map<String, QueryDimensionType> getRelatedDimensionMap() {
        return this.relatedDimensionMap;
    }

    public void setRelatedDimensionMap(Map<String, QueryDimensionType> relatedDimensionMap) {
        this.relatedDimensionMap = relatedDimensionMap;
    }
}

