/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj
 */
package com.jiuqi.nr.formulamapping.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj;
import java.util.List;

public interface IQueryFormulaService {
    public List<QueryFormulaObj> queryFormulas(String var1, String var2) throws JQException;

    public List<QueryFormulaObj> queryChildrenFormulas(String var1, QueryFormulaObj var2) throws JQException;
}

