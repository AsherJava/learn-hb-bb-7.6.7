/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionStyleField;
import com.jiuqi.nr.zbquery.model.LinkField;
import com.jiuqi.nr.zbquery.model.OrderField;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryLayout;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZBQueryModel {
    public static final int VERSION_1 = 1;
    public static final int VERSION_2 = 2;
    public static final int VERSION_CUR = 2;
    private int version = 2;
    private List<QueryObject> queryObjects = new ArrayList<QueryObject>();
    private List<QueryDimension> dimensions = new ArrayList<QueryDimension>();
    private List<ConditionField> conditions = new ArrayList<ConditionField>();
    private QueryLayout layout = new QueryLayout();
    private List<OrderField> orders = new ArrayList<OrderField>();
    private List<ConditionStyleField> conditionStyles = new ArrayList<ConditionStyleField>();
    private List<LinkField> hyperlinks = new ArrayList<LinkField>();
    private QueryOption option = new QueryOption();
    private String description;
    private Map<String, String> extendedDatas = new HashMap<String, String>();

    public List<QueryObject> getQueryObjects() {
        return this.queryObjects;
    }

    public void setQueryObjects(List<QueryObject> queryObjects) {
        this.queryObjects = queryObjects;
    }

    public List<QueryDimension> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(List<QueryDimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<ConditionField> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<ConditionField> conditions) {
        this.conditions = conditions;
    }

    public QueryLayout getLayout() {
        return this.layout;
    }

    public void setLayout(QueryLayout layout) {
        this.layout = layout;
    }

    public List<OrderField> getOrders() {
        return this.orders;
    }

    public void setOrders(List<OrderField> orders) {
        this.orders = orders;
    }

    public QueryOption getOption() {
        return this.option;
    }

    public void setOption(QueryOption option) {
        this.option = option;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LinkField> getHyperlinks() {
        return this.hyperlinks;
    }

    public void setHyperlinks(List<LinkField> hyperlinks) {
        this.hyperlinks = hyperlinks;
    }

    public Map<String, String> getExtendedDatas() {
        return this.extendedDatas;
    }

    public void setExtendedDatas(Map<String, String> extendedDatas) {
        this.extendedDatas = extendedDatas;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<ConditionStyleField> getConditionStyles() {
        return this.conditionStyles;
    }

    public void setConditionStyles(List<ConditionStyleField> conditionStyles) {
        this.conditionStyles = conditionStyles;
    }
}

