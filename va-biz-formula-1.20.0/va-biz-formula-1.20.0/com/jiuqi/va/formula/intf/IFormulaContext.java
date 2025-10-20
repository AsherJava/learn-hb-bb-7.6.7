/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 */
package com.jiuqi.va.formula.intf;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import java.util.Map;
import java.util.stream.Stream;

public interface IFormulaContext
extends IContext {
    public Object get(String var1);

    public void put(String var1, Object var2);

    public Map<String, Object> getDimValues();

    public Object findRefFieldValue(int var1, String var2, String var3, String var4);

    public Object findRefFieldValue(int var1, String var2, String var3, String var4, Map<String, Object> var5);

    public Map<String, Map<String, Object>> getAll(int var1, String var2);

    public Stream<Map<String, Object>> getRefDataByExpression(int var1, String var2, String var3);

    public Object valueOf(Object var1, int var2);

    public Object analyticalValue(Object var1, int var2);

    public int getRefTableType(String var1);

    public DataModelDO findBaseDataDefine(String var1);

    public String getTenantName();
}

