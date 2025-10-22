/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.np.definition.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import java.util.List;

public interface IDataDefinitionDesignTimeController {
    public DesignTableDefine queryTableDefine(String var1) throws JQException;

    public DesignTableDefine queryTableDefinesByCode(String var1) throws JQException;

    public List<DesignTableDefine> queryTableDefines(String[] var1) throws JQException;

    public DesignFieldDefine queryFieldDefine(String var1) throws JQException;

    public DesignFieldDefine queryFieldDefineByCodeInTable(String var1, String var2) throws JQException;

    public List<DesignFieldDefine> getAllFieldsInTable(String var1) throws JQException;

    public List<DesignFieldDefine> queryFieldDefines(String[] var1) throws JQException;
}

