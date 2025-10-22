/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl.formula;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FM_CONDITION_DES")
@DBAnno.DBLink(linkWith=TaskDefine.class, linkField="key", field="taskKey")
public class DesignFormulaConditionImpl
implements DesignFormulaCondition {
    @DBAnno.DBField(dbField="FC_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="FC_CODE")
    private String code;
    @DBAnno.DBField(dbField="FC_TITLE")
    private String title;
    @DBAnno.DBField(dbField="FC_FORMULA")
    private String formulaCondition;
    @DBAnno.DBField(dbField="FC_ORDER")
    private String order;
    @DBAnno.DBField(dbField="FC_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="FC_TASK")
    private String taskKey;

    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getFormulaCondition() {
        return this.formulaCondition;
    }

    @Override
    public void setFormulaCondition(String formulaCondition) {
        this.formulaCondition = formulaCondition;
    }

    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }
}

