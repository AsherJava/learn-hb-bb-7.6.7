/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.dafafill.entity.DataFillData;
import com.jiuqi.nr.dafafill.model.DataFillModel;

public interface IDataFillDataService {
    public void add(DataFillData var1) throws JQException;

    public void modify(DataFillData var1) throws JQException;

    public void delete(String var1) throws JQException;

    public DataFillData findByDefinition(String var1, String var2) throws JQException;

    public DataFillModel getModelByDefinition(String var1, String var2) throws JQException;

    public void saveModel(String var1, String var2, DataFillModel var3) throws JQException;
}

