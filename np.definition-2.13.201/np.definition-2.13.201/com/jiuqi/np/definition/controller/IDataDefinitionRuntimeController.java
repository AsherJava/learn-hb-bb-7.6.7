/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.controller;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import java.util.Collection;
import java.util.List;

public interface IDataDefinitionRuntimeController {
    public TableDefine queryTableDefine(String var1) throws Exception;

    public FieldDefine queryFieldDefine(String var1) throws Exception;

    public List<FieldDefine> queryFieldDefines(Collection<String> var1) throws Exception;

    public FieldDefine queryFieldByCodeInTable(String var1, String var2) throws Exception;

    public TableDefine queryTableDefineByCode(String var1) throws Exception;

    public FieldDefine queryFieldDefineByCodeInRange(Collection<String> var1, String var2) throws Exception;

    public List<TableDefine> queryTableDefinesByFields(Collection<String> var1);

    public List<FieldDefine> queryFieldDefinesInRange(Collection<String> var1);

    public List<TableDefine> queryTableDefinesInRange(Collection<String> var1) throws Exception;

    public boolean checkTableHasRecord(String var1);

    public int checkTableDefineIsDestry(String var1);

    public List<FieldDefine> getAllFieldsInTable(String var1) throws Exception;
}

