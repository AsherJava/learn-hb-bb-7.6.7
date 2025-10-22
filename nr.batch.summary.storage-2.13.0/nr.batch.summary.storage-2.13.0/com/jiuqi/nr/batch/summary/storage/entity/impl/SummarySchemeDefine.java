/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim;
import com.jiuqi.nr.batch.summary.storage.entity.SingleDim;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl;
import com.jiuqi.nr.batch.summary.storage.entity.serializer.SummarySchemeDeserializer;
import com.jiuqi.nr.batch.summary.storage.entity.serializer.SummarySchemeSerializer;
import com.jiuqi.util.StringUtils;
import java.util.List;

@JsonSerialize(using=SummarySchemeSerializer.class)
@JsonDeserialize(using=SummarySchemeDeserializer.class)
public class SummarySchemeDefine
extends SummarySchemeImpl
implements SummaryScheme {
    private String code;
    private String task;
    private String creator;
    private SchemeTargetDim targetDim;
    private String corporateEntityType;
    private String entityId;
    private List<SingleDim> singleDims;

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public SchemeTargetDim getTargetDim() {
        return this.targetDim;
    }

    public void setTargetDim(SchemeTargetDim targetDim) {
        this.targetDim = targetDim;
    }

    @Override
    public String getCorporateEntityType() {
        return this.corporateEntityType;
    }

    public void setCorporateEntityType(String corporateEntityType) {
        this.corporateEntityType = corporateEntityType;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public List<SingleDim> getSingleDims() {
        return this.singleDims;
    }

    public void setSingleDims(List<SingleDim> singleDims) {
        this.singleDims = singleDims;
    }

    @Override
    @JsonIgnore
    public boolean isValidScheme() {
        return super.isValidScheme() && StringUtils.isNotEmpty((String)this.code) && StringUtils.isNotEmpty((String)this.task) && this.targetDim != null && this.targetDim.isValidTargetDim();
    }
}

