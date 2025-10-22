/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.customentry.service;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.customentry.define.IndexObj;
import com.jiuqi.nr.customentry.service.CellData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CustomEntrySaveContext {
    public Map<String, Map<String, List<Map<String, Map<String, CellData>>>>> saveValues;
    public Map<String, Map<String, Map<String, FieldDefine>>> regionFields;
    public HashMap<Integer, IndexObj> indexRows;
    public Boolean hasBizKey = false;

    CustomEntrySaveContext() {
    }
}

