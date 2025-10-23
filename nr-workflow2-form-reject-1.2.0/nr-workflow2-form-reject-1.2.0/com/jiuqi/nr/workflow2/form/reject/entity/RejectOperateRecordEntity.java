/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 */
package com.jiuqi.nr.workflow2.form.reject.entity;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectOperateFormResultSet;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectOperateRecordEntity;
import java.util.Date;
import java.util.List;

public class RejectOperateRecordEntity
implements IRejectOperateRecordEntity,
IRejectOperateFormResultSet {
    private IFormObject formObject;
    private String optId;
    private String optUser;
    private Date optTime;
    private String optComment;
    private DimensionCombination combination;
    private List<String> optFormIds;

    public RejectOperateRecordEntity() {
    }

    public RejectOperateRecordEntity(DimensionCombination combination, List<String> optFormIds) {
        this.optFormIds = optFormIds;
        this.combination = combination;
    }

    @Override
    public IFormObject getFormObject() {
        return this.formObject;
    }

    public void setFormObject(IFormObject formObject) {
        this.formObject = formObject;
    }

    @Override
    public DimensionCombination getDimensionCombination() {
        return this.combination;
    }

    @Override
    public String getOptId() {
        return this.optId;
    }

    public void setOptId(String optId) {
        this.optId = optId;
    }

    @Override
    public String getOptUser() {
        return this.optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    @Override
    public Date getOptTime() {
        return this.optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    @Override
    public String getOptComment() {
        return this.optComment;
    }

    @Override
    public List<String> getOptFromIds() {
        return this.optFormIds;
    }

    public void setOptComment(String optComment) {
        this.optComment = optComment;
    }
}

