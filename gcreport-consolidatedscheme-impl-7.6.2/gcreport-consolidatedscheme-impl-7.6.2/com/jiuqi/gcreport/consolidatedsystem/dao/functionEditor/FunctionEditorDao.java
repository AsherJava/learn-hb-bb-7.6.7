/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.functionEditor;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.functionEditor.GcFunctionEditorEO;
import java.util.List;

public interface FunctionEditorDao
extends IDbSqlGenericDAO<GcFunctionEditorEO, String> {
    public List<GcFunctionEditorEO> queryByUserId(String var1, Integer var2, Integer var3);

    public int queryCountByUserId(String var1);
}

