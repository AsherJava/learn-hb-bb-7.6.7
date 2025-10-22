/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.data.engine.summary.define;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.data.engine.summary.define.DimRelationInfo;
import com.jiuqi.nr.data.engine.summary.define.RelationInfo;

public interface IRelationInfoProvider {
    public String getType();

    public DimRelationInfo findDimRelationInfo(ExecutorContext var1, String var2) throws Exception;

    public RelationInfo findRelationInfo(ExecutorContext var1, String var2, String var3) throws Exception;

    public String findMainTableName(ExecutorContext var1, String var2) throws Exception;
}

