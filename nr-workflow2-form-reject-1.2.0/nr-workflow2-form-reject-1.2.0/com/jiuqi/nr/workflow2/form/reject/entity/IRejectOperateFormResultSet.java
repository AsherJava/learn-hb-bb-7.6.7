/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.form.reject.entity;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Date;
import java.util.List;

public interface IRejectOperateFormResultSet {
    public DimensionCombination getDimensionCombination();

    public String getOptId();

    public String getOptUser();

    public Date getOptTime();

    public String getOptComment();

    public List<String> getOptFromIds();
}

