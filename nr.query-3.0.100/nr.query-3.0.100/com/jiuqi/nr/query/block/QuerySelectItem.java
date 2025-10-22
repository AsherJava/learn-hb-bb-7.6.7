/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.block.QueryItemSortDefine;
import com.jiuqi.nr.query.common.BusinessObject;
import com.jiuqi.nr.query.common.OrderType;
import com.jiuqi.nr.query.deserializer.QuerySelectItemDeserializer;
import com.jiuqi.nr.query.serializer.QuerySelectItemSerializer;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=QuerySelectItemSerializer.class)
@JsonDeserialize(using=QuerySelectItemDeserializer.class)
public class QuerySelectItem
extends BusinessObject {
    private static final Logger log = LoggerFactory.getLogger(QuerySelectItem.class);
    public static final String SELECTITEM_DEMESIONID = "demesionId";
    public static final String SELECTITEM_CODE = "code";
    public static final String SELECTITEM_TITLE = "title";
    public static final String SELECTITEM_ISORDER = "isorder";
    public static final String SELECTITEM_ORDERTYPE = "ordertype";
    public static final String SELECTITEM_CUSTOM = "iscustom";
    public static final String SELECTITEM_CUSTOMVALUE = "customvalue";
    public static final String SELECTITEM_ORDER = "order";
    public static final String SELECTITEM_SORT = "sort";
    public static final String SELECTITEM_ISSORTED = "issorted";
    public static final String SELECTITEM_PARENTPATH = "parentpath";
    private String demesionId;
    private String code;
    public boolean isSorted;
    private QueryItemSortDefine sort;
    private boolean isOrder = false;
    private OrderType orderType = OrderType.ORDER_ASC;
    private boolean isCustom = false;
    private String customvalue;
    private String parentPath;

    public void setIsSorted(boolean sorted) {
        this.isSorted = sorted;
    }

    public boolean getIsSorted() {
        return this.isSorted;
    }

    public QueryItemSortDefine getSort() {
        return this.sort;
    }

    public void setSort(QueryItemSortDefine sort) {
        this.sort = sort;
    }

    public void setSort(String sortInfor) {
        try {
            this.sort = this.convertJsonToBlckInfo(sortInfor);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private QueryItemSortDefine convertJsonToBlckInfo(String sortInfor) {
        try {
            if (StringUtil.isNullOrEmpty((String)sortInfor)) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            QueryItemSortDefine item = (QueryItemSortDefine)objectMapper.readValue(sortInfor, QueryItemSortDefine.class);
            return item;
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean isOrder() {
        return this.isOrder;
    }

    public void setOrder(boolean isOrder) {
        this.isOrder = isOrder;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public boolean getCustom() {
        return this.isCustom;
    }

    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public String getCustomValue() {
        return this.customvalue;
    }

    public void setCustomValue(String customValue) {
        this.customvalue = customValue;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getParentPath() {
        return this.parentPath;
    }

    public String getDemesionId() {
        return this.demesionId;
    }

    public void setDemesionId(String demesionId) {
        this.demesionId = demesionId;
    }

    public String getCode() {
        if (StringUtil.isNullOrEmpty((String)this.code)) {
            this.code = this.getTitle();
        }
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

