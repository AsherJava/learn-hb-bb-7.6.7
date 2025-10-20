/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.FormulaFunctionDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMULAFUNCTION")
public class DesignFormulaFunctionDefineImpl
implements FormulaFunctionDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="ff_name")
    private String name;
    @DBAnno.DBField(dbField="ff_title")
    private String title;
    @DBAnno.DBField(dbField="ff_group")
    private String group;
    @DBAnno.DBField(dbField="ff_application")
    private String application;
    @DBAnno.DBField(dbField="ff_example")
    private String example;
    @DBAnno.DBField(dbField="ff_function")
    private String function;
    @DBAnno.DBField(dbField="ff_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ff_order")
    private String order;
    @DBAnno.DBField(dbField="ff_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="ff_version")
    private String version;
    @DBAnno.DBField(dbField="ff_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="ff_functiontype")
    private int functiontype;

    @Override
    public int getFuntionType() {
        return this.functiontype;
    }

    @Override
    public void setFuntionType(int funtiontype) {
        this.functiontype = funtiontype;
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
    public String getGroup() {
        return this.group;
    }

    @Override
    public String getApplication() {
        return this.application;
    }

    @Override
    public String getExample() {
        return this.example;
    }

    @Override
    public String getFunction() {
        return this.function;
    }

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
    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public void setApplication(String application) {
        this.application = application;
    }

    @Override
    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public void setFunction(String function) {
        this.function = function;
    }
}

