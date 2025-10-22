/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package com.jiuqi.nr.batch.summary.storage.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim;
import com.jiuqi.nr.batch.summary.storage.entity.SingleDim;
import com.jiuqi.nr.batch.summary.storage.entity.SummarySchemeDes;
import com.jiuqi.nr.batch.summary.storage.entity.serializer.SummarySchemeDeserializer;
import java.util.List;

@JsonDeserialize(using=SummarySchemeDeserializer.class)
public interface SummaryScheme
extends SummarySchemeDes {
    public static final String TYPE = "scheme";

    public String getCode();

    public String getTask();

    public String getCreator();

    public SchemeTargetDim getTargetDim();

    @Deprecated
    public String getCorporateEntityType();

    public String getEntityId();

    public List<SingleDim> getSingleDims();
}

