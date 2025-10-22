/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.np.dataengine.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.parse.ReadWriteInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;

public interface IReportFunction {
    default public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.AUTO;
    }

    default public ReadWriteInfo getReadWriteInfo(QueryContext qContext, List<IASTNode> params) {
        return null;
    }

    default public String getDescription() {
        return null;
    }

    default public String getExample() {
        return null;
    }
}

