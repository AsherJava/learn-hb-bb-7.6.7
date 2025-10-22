/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IEntityRow
 */
package com.jiuqi.nr.query.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.deserializer.QueryRunTimeCacheDeserializer;
import com.jiuqi.nr.query.serializer.QueryRunTimeCacheSerializer;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@JsonSerialize(using=QueryRunTimeCacheSerializer.class)
@JsonDeserialize(using=QueryRunTimeCacheDeserializer.class)
public class QueryRunTimeCache
implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(QueryRunTimeCache.class);
    public static String CONST_DIMENSIONID = "dimid";
    public static String CONST_ISFROMDATA = "isFromData";
    public static String CONST_DIMENSIONSET = "dimensionSet";
    public static String CONST_DEPTH = "depth";
    public static String CONST_ISSUMROW = "isSumRow";
    public static String CONST_HASCHILD = "hasChild";
    public static String CONST_DIMNAME = "dimName";
    public static String CONST_ISFIRSTDIM = "isfirstdim";
    public static String CONST_TITLE = "title";
    public static String CONST_ISTREE = "isTree";
    public static String CONST_ISLAST = "isLast";
    String dimensionId;
    boolean isFromData = false;
    DimensionValueSet dimensionSet;
    int depth = -1;
    boolean isSumRow = false;
    boolean hasChild = false;
    boolean isDimShow = true;
    String dimName;
    boolean isFirstDimension;
    private String title;
    private IEntityRow row;
    boolean isTree;
    boolean isLast;
    boolean showSum;
    private List<QuerySelectItem> conditionItems;

    public void setIsFirstDimension(boolean isFirst) {
        this.isFirstDimension = isFirst;
    }

    public boolean getIsFirstDimension() {
        return this.isFirstDimension;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionId() {
        return this.dimensionId;
    }

    public boolean getIsFromData() {
        return this.isFromData;
    }

    public void setIsFromData(boolean isFromData) {
        this.isFromData = isFromData;
    }

    public void setDimensionSet(DimensionValueSet dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public DimensionValueSet getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return this.depth;
    }

    public boolean getIsSumRow() {
        return this.isSumRow;
    }

    public void setIsSumRow(boolean isSumRow) {
        this.isSumRow = isSumRow;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public boolean getHasChild() {
        return this.hasChild;
    }

    public boolean getIsDimShow() {
        return this.isDimShow;
    }

    public void setIsDimShow(boolean isDimShow) {
        this.isDimShow = isDimShow;
    }

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setRow(IEntityRow row) {
        this.row = row;
    }

    public IEntityRow getRow() {
        return this.row;
    }

    public void setIsTree(boolean isTree) {
        this.isTree = isTree;
    }

    public boolean getIsTree() {
        return this.isTree;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public boolean getIsLast() {
        return this.isLast;
    }

    public void setShowSum(boolean showSum) {
        this.showSum = showSum;
    }

    public boolean isShowSum() {
        return this.showSum;
    }

    public void setConditionItems(List<QuerySelectItem> conditionItems) {
        this.conditionItems = conditionItems;
    }

    public List<QuerySelectItem> getConditionItems() {
        return this.conditionItems;
    }

    public QueryRunTimeCache clone() {
        QueryRunTimeCache cache = null;
        try {
            cache = (QueryRunTimeCache)super.clone();
        }
        catch (CloneNotSupportedException e) {
            logger.error("\u514b\u9686\u7f13\u5b58", e);
        }
        return cache;
    }

    public boolean equals(Object obj) {
        if (obj instanceof QueryRunTimeCache) {
            QueryRunTimeCache queryRunTimeCache = (QueryRunTimeCache)obj;
            return this.title.equals(queryRunTimeCache.getTitle());
        }
        return false;
    }
}

