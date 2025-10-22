/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.FormulaFunctionParamterDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FFUNCTION")
public class DesignFormulaFunctionParamterDefineImpl
implements FormulaFunctionParamterDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="ffp_name")
    private String name;
    @DBAnno.DBField(dbField="ffp_title")
    private String title;
    @DBAnno.DBField(dbField="ffp_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ffp_order")
    private String order;
    @DBAnno.DBField(dbField="ffp_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="ffp_version")
    private String version;
    @DBAnno.DBField(dbField="ffp_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="ffp_type")
    private int type;

    public String getKey() {
        return this.key;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public void setType(int dataType) {
        this.type = dataType;
    }
}

