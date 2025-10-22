/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.param;

import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import java.util.List;

public interface IEntityDeleteParam
extends IEntityQueryParam {
    public List<EntityDataRow> getDeleteRows();
}

