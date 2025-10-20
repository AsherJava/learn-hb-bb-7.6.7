/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.model;

import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType;
import com.jiuqi.nvwa.framework.parameter.model.DataBusinessType;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractParameterDataSourceModel
implements Cloneable {
    protected ParameterDataSourceHierarchyType hierarchyType = ParameterDataSourceHierarchyType.NONE;
    protected String refObject;
    protected DataBusinessType businessType = DataBusinessType.NONE;
    protected boolean timekey;
    protected int timegranularity = -1;
    protected int dataType;
    protected boolean canOrder = false;

    public abstract String getType();

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public void setHierarchyType(ParameterDataSourceHierarchyType hierarchyType) {
        this.hierarchyType = hierarchyType;
    }

    public ParameterDataSourceHierarchyType getHierarchyType() {
        return this.hierarchyType;
    }

    public void setRefObject(String refObject) {
        this.refObject = refObject;
    }

    public String getRefObject() {
        return this.refObject;
    }

    public boolean isTimekey() {
        return this.timekey;
    }

    public void setTimekey(boolean timekey) {
        this.timekey = timekey;
    }

    public void setBusinessType(DataBusinessType businessType) {
        this.businessType = businessType;
    }

    public DataBusinessType getBusinessType() {
        return this.businessType;
    }

    public void setTimegranularity(int timegranularity) {
        this.timegranularity = timegranularity;
    }

    public int getTimegranularity() {
        return this.timegranularity;
    }

    public boolean isCanOrder() {
        return this.canOrder;
    }

    public void setCanOrder(boolean canOrder) {
        this.canOrder = canOrder;
    }

    public final void fromJson(JSONObject json) throws JSONException {
        this.dataType = json.optInt("dataType");
        this.hierarchyType = ParameterDataSourceHierarchyType.valueOf(json.optInt("hierType", ParameterDataSourceHierarchyType.NONE.value()));
        this.refObject = json.optString("refObject", null);
        this.timekey = json.optBoolean("isTimekey");
        this.timegranularity = json.optInt("timegranularity", -1);
        this.businessType = DataBusinessType.valueOf(json.optInt("dimType", 0));
        this.canOrder = json.optBoolean("canOrder", false);
        this.fromExtJson(json);
    }

    public final void toJson(JSONObject json) throws JSONException {
        json.put("type", (Object)this.getType());
        json.put("dataType", this.getDataType());
        json.put("hierType", this.getHierarchyType().value());
        json.put("dimType", this.getBusinessType().value());
        json.put("isTimekey", this.timekey);
        json.put("canOrder", this.canOrder);
        if (this.refObject != null) {
            json.put("refObject", (Object)this.refObject);
        }
        if (this.timegranularity != -1) {
            json.put("timegranularity", this.timegranularity);
        }
        this.toExtJson(json);
    }

    public boolean hasHierarchy() {
        return this.hierarchyType == ParameterDataSourceHierarchyType.PARENTMODE || this.hierarchyType == ParameterDataSourceHierarchyType.STRUCTURECODE;
    }

    protected void fromExtJson(JSONObject json) throws JSONException {
    }

    protected void toExtJson(JSONObject json) throws JSONException {
    }

    public AbstractParameterDataSourceModel clone() {
        try {
            return (AbstractParameterDataSourceModel)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

