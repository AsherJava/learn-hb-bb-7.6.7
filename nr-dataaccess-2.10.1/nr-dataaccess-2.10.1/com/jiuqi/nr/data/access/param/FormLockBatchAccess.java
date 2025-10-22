/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.FormLockBatchReadWriteResult;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FormLockBatchAccess
implements IBatchAccess {
    private List<FormLockBatchReadWriteResult> resultList;
    private Map<DimensionValueSet, Set<String>> cache;
    private DimensionCombination previousKey = null;
    private DimensionValueSet previousValueSet = null;

    @Override
    public AccessCode getAccessCode(DimensionCombination masterKey, String formKey) {
        AccessCode accessCode = new AccessCode("formLock");
        if (this.cache == null) {
            this.cache = new HashMap<DimensionValueSet, Set<String>>();
            for (FormLockBatchReadWriteResult result : this.resultList) {
                Set formLock = this.cache.computeIfAbsent(result.getDimensionValueSet(), k -> new HashSet());
                String resultFormKey = result.getFormKey();
                if (!result.isLock()) continue;
                formLock.add(resultFormKey);
            }
            this.resultList = null;
        }
        DimensionValueSet findKey = null;
        if (this.previousKey != null && this.previousKey == masterKey) {
            findKey = this.previousValueSet;
        }
        if (findKey == null) {
            findKey = masterKey.toDimensionValueSet();
        }
        this.previousKey = masterKey;
        this.previousValueSet = findKey;
        Set<String> formLock = this.cache.get(findKey);
        if (formLock != null && formLock.contains(formKey)) {
            accessCode.setCode("2");
        }
        return accessCode;
    }

    public void setResultList(List<FormLockBatchReadWriteResult> formLockBatchReadWriteResult) {
        this.resultList = formLockBatchReadWriteResult;
    }
}

