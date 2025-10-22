/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.data.engine.summary.exe.impl;

import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.data.engine.summary.define.IRelationInfoProvider;
import com.jiuqi.nr.data.engine.summary.exe.impl.SumDataSetBuilder;
import com.jiuqi.nr.data.engine.summary.exe.impl.SumQueryTable;
import java.sql.Connection;

@Deprecated
public class DuplicateCountDataSetBuilder
extends SumDataSetBuilder {
    public DuplicateCountDataSetBuilder(ExecutorContext executorContext, ISQLInfo dbInfo, DimensionValueSet srcDimension, SumQueryTable sumQueryTable, IRelationInfoProvider relationInfoProvider, Connection connection) {
        super(executorContext, dbInfo, srcDimension, sumQueryTable, relationInfoProvider, connection);
    }

    @Override
    public String buildDataSetSql() throws Exception {
        StringBuilder sql = new StringBuilder();
        return sql.toString();
    }
}

