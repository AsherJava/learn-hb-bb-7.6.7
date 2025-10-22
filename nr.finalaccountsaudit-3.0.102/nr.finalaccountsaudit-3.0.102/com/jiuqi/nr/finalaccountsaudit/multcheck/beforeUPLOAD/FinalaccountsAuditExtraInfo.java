/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD;

import com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD.FinalaccountsAuditExtraInfoParam;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import java.io.Serializable;
import java.util.List;

public class FinalaccountsAuditExtraInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String asyncTaskId;
    private List<MultCheckItem> checkItems;
    private String date;
    private String formSchemeKey;
    private String formSchemeTitle;
    private EntityViewData masterEntity;
    private FinalaccountsAuditExtraInfoParam param;
    private String period;
    private List<String> selectEntityNum;
    private String taskKey;
    private String taskName;

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public List<MultCheckItem> getCheckItems() {
        return this.checkItems;
    }

    public void setCheckItems(List<MultCheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String fromSchemeKey) {
        this.formSchemeKey = fromSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public FinalaccountsAuditExtraInfoParam getParam() {
        return this.param;
    }

    public void setParam(FinalaccountsAuditExtraInfoParam param) {
        this.param = param;
    }

    public List<String> getSelectEntityNum() {
        return this.selectEntityNum;
    }

    public void setSelectEntityNum(List<String> selectEntityNum) {
        this.selectEntityNum = selectEntityNum;
    }

    public EntityViewData getMasterEntity() {
        return this.masterEntity;
    }

    public void setMasterEntity(EntityViewData masterEntity) {
        this.masterEntity = masterEntity;
    }
}

