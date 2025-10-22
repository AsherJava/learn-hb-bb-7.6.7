/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity;

import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeUnit;
import java.util.Date;

public interface SummarySchemeDes {
    public String getKey();

    public String getTitle();

    public String getGroup();

    public Date getUpdateTime();

    public Date getSumDataTime();

    public String getOrdinal();

    public SchemeRangeUnit getRangeUnit();

    public SchemeRangeForm getRangeForm();
}

