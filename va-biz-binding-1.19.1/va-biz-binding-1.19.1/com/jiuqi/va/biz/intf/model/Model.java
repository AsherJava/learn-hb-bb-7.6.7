/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.ref.RefDataBuffer;
import com.jiuqi.va.biz.intf.ref.RefDataFilter;
import com.jiuqi.va.biz.intf.value.TypedContainer;
import com.jiuqi.va.biz.ref.intf.RefDataContext;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface Model {
    public ModelContext getContext();

    public ModelDefine getDefine();

    public TypedContainer<Plugin> getPlugins();

    public RefDataBuffer getRefDataBuffer();

    @Deprecated
    public Map<String, Object> getRefValue(int var1, String var2, String var3);

    @Deprecated
    public Map<String, Object> matchValue(int var1, String var2, String var3);

    @Deprecated
    public Map<String, Object> toViewValue(int var1, String var2, String var3, Map<String, Object> var4);

    @Deprecated
    default public Map<String, Object> toViewValue(int refTableType, String refTableName, Map<String, Object> value) {
        return this.toViewValue(refTableType, refTableName, null, value);
    }

    @Deprecated
    public Object findRefFieldValue(int var1, String var2, String var3, String var4);

    @Deprecated
    public Stream<Map<String, Object>> findRefObjectsByParam(int var1, String var2, Map<String, Object> var3);

    @Deprecated
    public Stream<Map<String, Object>> findRefObjectsByExpression(int var1, String var2, String var3);

    @Deprecated
    public Stream<Map<String, Object>> findRefObjectsByFilter(int var1, String var2, RefDataFilter var3);

    @Deprecated
    public Map<String, Map<String, Object>> getAll(int var1, String var2);

    public List<String> createVerifyCodes(List<String> var1);

    public String createVerifyCode(String var1);

    default public Map<String, Object> getDimValues(DataFieldDefine fieldDefine, DataRow row) {
        throw new UnsupportedOperationException();
    }

    default public Map<String, Object> getDimValues() {
        throw new UnsupportedOperationException();
    }

    default public RefDataContext getRefContext() {
        Map<String, Object> dimValues = this.getDimValues();
        return this.buildRefDataContext(dimValues);
    }

    default public RefDataContext getRefDataContext(DataFieldDefine fieldDefine, DataRow row) {
        Map<String, Object> dimValues = this.getDimValues(fieldDefine, row);
        return this.buildRefDataContext(dimValues);
    }

    default public RefDataContext buildRefDataContext(Map<String, Object> dimValues) {
        RefDataContext context = new RefDataContext();
        for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
            if (entry.getKey().equals("BIZDATE")) {
                Date bizDate = (Date)entry.getValue();
                context.setBizDate(bizDate);
                continue;
            }
            context.set(entry.getKey().toLowerCase(), entry.getValue());
        }
        return context;
    }
}

