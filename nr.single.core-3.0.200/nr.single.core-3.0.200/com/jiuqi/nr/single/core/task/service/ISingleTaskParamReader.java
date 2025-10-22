/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task.service;

import com.jiuqi.nr.single.core.task.model.SingleTableInfo;
import java.util.List;

public interface ISingleTaskParamReader {
    public boolean isIncloudParam();

    public List<SingleTableInfo> getSingleTableInfos();
}

