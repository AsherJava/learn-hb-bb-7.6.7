/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.api;

import com.jiuqi.nr.dataentity_ext.api.IEntityQuery;
import com.jiuqi.nr.dataentity_ext.common.EntityDataException;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow;
import com.jiuqi.nr.dataentity_ext.dto.QueryParam;
import com.jiuqi.nr.dataentity_ext.dto.SaveParam;
import java.util.List;

public interface IEntityDataService {
    public String save(SaveParam var1, List<IEntityDataRow> var2) throws EntityDataException;

    public IEntityQuery getEntityQuery(QueryParam var1) throws EntityDataException;

    public void drop(String var1);

    public void dropByTime(long var1);
}

