/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.entity.model.IEntityDefine;
import java.util.List;

public interface IGetEntityInfoService {
    public List<IEntityDefine> getEntityInfoByTaskList(List<String> var1);
}

