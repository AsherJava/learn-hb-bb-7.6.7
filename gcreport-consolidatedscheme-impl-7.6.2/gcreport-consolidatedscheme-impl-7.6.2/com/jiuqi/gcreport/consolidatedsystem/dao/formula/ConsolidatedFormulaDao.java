/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.formula;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.Formula.ConsolidatedFormulaEO;
import java.util.List;

public interface ConsolidatedFormulaDao
extends IDbSqlGenericDAO<ConsolidatedFormulaEO, String> {
    public List<ConsolidatedFormulaEO> listConsFormulas(String var1);

    public void batchDeleteByIds(List<String> var1);

    public ConsolidatedFormulaEO getPreNodeBySystemIdAndOrder(String var1, String var2);

    public ConsolidatedFormulaEO getNextNodeBySystemIdAndOrder(String var1, String var2);
}

