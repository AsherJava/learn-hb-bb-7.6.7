/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.singlequeryimport.intf.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.io.Serializable;

@DBAnno.DBTable(dbTable="query_model_explain")
public class QueryModelExplain
implements Serializable {
    @DBAnno.DBField(dbField="id", isPk=true)
    private String id;
    @DBAnno.DBField(dbField="model_id")
    private String modelId;
    @DBAnno.DBField(dbField="period")
    private String period;
    @DBAnno.DBField(dbField="explain")
    private String explain;
    @DBAnno.DBField(dbField="model_name")
    private String modelName;
    @DBAnno.DBField(dbField="org_code")
    private String orgCode;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getExplain() {
        return this.explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}

