/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentry.paramInfo.AccessResult;
import com.jiuqi.nr.dataentry.readwrite.IAccessResult;
import com.jiuqi.nr.dataentry.readwrite.IFieldReadWriteAccessResult;
import java.util.Map;
import java.util.Set;

public class FieldReadWriteAccessResult
implements IFieldReadWriteAccessResult {
    private Map<DimensionValueSet, Map<String, String>> dimNoAccessMap;
    private Map<String, Set<String>> filedFormKeysMap;

    public FieldReadWriteAccessResult(Map<DimensionValueSet, Map<String, String>> dimNoAccessMap, Map<String, Set<String>> filedFormKeysMap) {
        this.dimNoAccessMap = dimNoAccessMap;
        this.filedFormKeysMap = filedFormKeysMap;
    }

    @Override
    public IAccessResult getAccessResult(DimensionValueSet dimensionValueSet, String filedKey) {
        Map<String, String> noAccessReasonsMap = this.dimNoAccessMap.get(dimensionValueSet);
        Set<String> formKeys = this.filedFormKeysMap.get(filedKey);
        if (null == noAccessReasonsMap || null == formKeys) {
            return new AccessResult();
        }
        for (String formKey : formKeys) {
            String reason = noAccessReasonsMap.get(formKey);
            if (null == reason) continue;
            AccessResult accessResult = new AccessResult(false, reason);
            return accessResult;
        }
        return new AccessResult();
    }
}

