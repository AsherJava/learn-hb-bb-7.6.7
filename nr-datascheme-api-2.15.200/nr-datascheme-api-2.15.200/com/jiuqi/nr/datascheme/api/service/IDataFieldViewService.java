/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.DataField;
import java.util.List;
import java.util.Map;

public interface IDataFieldViewService {
    public Map<String, String> getAllFieldViewColumns(boolean var1);

    public List<Map<String, Object>> getFieldViewData(List<String> var1);

    public List<Map<String, Object>> getFieldViewData(List<DataField> var1, String var2);
}

